package com.selloohub.leo.order.service;

import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderLine;
import com.selloohub.leo.order.model.OrderStatus;
import com.selloohub.leo.order.repository.OrderRepository;
import com.selloohub.leo.stock.dto.StockMovementResponse;
import com.selloohub.leo.stock.repository.StockMovementRepository;
import com.selloohub.leo.stock.service.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderPipelineService {

    private static final Logger log = LoggerFactory.getLogger(OrderPipelineService.class);
    private final OrderRepository orderRepository;
    private final StockMovementRepository stockMovementRepository;
    private final StockService stockService;

    public OrderPipelineService(OrderRepository orderRepository, StockMovementRepository stockMovementRepository, StockService stockService) {
        this.orderRepository = orderRepository;
        this.stockMovementRepository = stockMovementRepository;
        this.stockService = stockService;
    }

    public void runPipeline(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        if (order.getStatus() == OrderStatus.PAID) {
            order = orderRepository.transitionStatus(orderId, OrderStatus.PAID, OrderStatus.PROCESSING)
                    .orElseThrow(() -> new ConflictException("ORDER_INVALID_STATE", "Order status changed before pipeline could start"));
        } else if (order.getStatus() != OrderStatus.PROCESSING) {
            throw new ConflictException("ORDER_INVALID_STATE", "Cannot run pipeline from status " + order.getStatus());
        }

        runStockStep(order);
        runCommissionStep(order);
        runAttributionStep(order);
        runNotifyStep(order);

        if (!order.isFulfillmentHold()) {
            orderRepository.transitionStatus(orderId, OrderStatus.PROCESSING, OrderStatus.READY_FOR_PICKUP);
        }

    }

    private void runStockStep(Order order) {
        boolean anyFailure = false;

        for (OrderLine line : order.getLines()) {
            if (stockMovementRepository.existsByOrderIdAndProductId(order.getId(), line.getProductId())) {
                continue;
            }

            Optional<StockMovementResponse> result = stockService.decrementForSale(line.getProductId(), line.getQty(), order.getId());
            if (result.isEmpty()) {
                anyFailure = true;
                log.warn("pipeline: insufficient stock, order={} product={}", order.getOrderNo(), line.getProductId());
            }
        }

        order.setFulfillmentHold(anyFailure);
        order.setHoldReason(anyFailure ? "INSUFFICIENT_STOCK" : null);
        orderRepository.save(order);
    }

    private void runCommissionStep(Order order) {
        log.info("pipeline: commission step stub - order={}", order.getOrderNo());
    }

    private void runAttributionStep(Order order) {
        log.info("pipeline: attribution step stub - order={}", order.getOrderNo());
    }

    private void runNotifyStep(Order order) {
        log.info("pipeline: notify step stub - order={}", order.getOrderNo());
    }
}
