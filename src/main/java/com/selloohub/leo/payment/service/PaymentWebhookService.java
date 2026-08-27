package com.selloohub.leo.payment.service;

import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;
import com.selloohub.leo.order.repository.OrderRepository;
import com.selloohub.leo.order.service.OrderPipelineService;
import com.selloohub.leo.payment.gateway.GatewayStatus;
import com.selloohub.leo.payment.gateway.PaymentGatewayClient;
import com.selloohub.leo.payment.model.PaymentSource;
import com.selloohub.leo.payment.model.PaymentStatus;
import com.selloohub.leo.payment.model.PaymentTxn;
import com.selloohub.leo.payment.repository.PaymentTxnRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.Optional;

@Service
public class PaymentWebhookService {

    private static final Logger log = LoggerFactory.getLogger(PaymentWebhookService.class);
    private final PaymentGatewayClient paymentGatewayClient;
    private final PaymentTxnRepository paymentTxnRepository;
    private final OrderRepository orderRepository;
    private final OrderPipelineService orderPipelineService;

    public PaymentWebhookService(PaymentGatewayClient paymentGatewayClient, PaymentTxnRepository paymentTxnRepository, OrderRepository orderRepository, OrderPipelineService orderPipelineService) {
        this.paymentGatewayClient = paymentGatewayClient;
        this.paymentTxnRepository = paymentTxnRepository;
        this.orderRepository = orderRepository;
        this.orderPipelineService = orderPipelineService;
    }

    public String handlePaymentConfirmation(String billCode, PaymentSource source) {

        GatewayStatus status = paymentGatewayClient.getStatus(billCode);

        if (paymentTxnRepository.existsByGatewayRef(status.gatewayRef())) {
            return "duplicate, acknowledged";
        }

        Order order = orderRepository.findByPaymentBillCode(billCode)
                .orElseThrow(() -> new ResourceNotFoundException("No order for billCode " + billCode));

        if (status.normalized() != PaymentStatus.PAID) {
            recordTxn(order, billCode, status, source);
            return "acknowledge, status=" + status.normalized();
        }

        if (status.amount().compareTo(order.getGrandTotal()) != 0) {
            recordTxn(order, billCode, status, source);
            log.error("payment amount mismatch order={} expected={} actual={}", order.getOrderNo(), order.getGrandTotal(), status.amount());
            throw new ConflictException("PAYMENT_AMOUNT_MISMATCH", "Amount does not match order total");
        }

        recordTxn(order, billCode, status, source);

        Optional<Order> flipped = orderRepository.transitionStatus(order.getId(), OrderStatus.PENDING_PAYMENT, OrderStatus.PAID);
        if (flipped.isEmpty()) {
            return "already processed, no-op";
        }

        Order paidOrder = flipped.get();
        paidOrder.getPayment().setPaidAt(Instant.now());
        paidOrder.getPayment().setGatewayRef(status.gatewayRef());
        orderRepository.save(paidOrder);

        orderPipelineService.runPipeline(paidOrder.getId());
        return "PAID, pipeline ran";

    }

    private void recordTxn(Order order, String billCode, GatewayStatus status, PaymentSource source) {
        paymentTxnRepository.save(new PaymentTxn(
                order.getId(),
                order.getOrderNo(),
                order.getPayment().getGateway(),
                billCode,
                status.gatewayRef(),
                status.amount(),
                status.normalized().toString(),
                status.normalized(),
                source,
                Map.of()
        ));
    }
}
