package com.selloohub.leo.order.service;

import com.selloohub.leo.agent.model.AgentStatus;
import com.selloohub.leo.agent.repository.AgentRepository;
import com.selloohub.leo.common.exception.ConflictException;
import com.selloohub.leo.common.exception.ResourceNotFoundException;
import com.selloohub.leo.common.util.MoneyMath;
import com.selloohub.leo.common.util.PhoneNormalizer;
import com.selloohub.leo.fulfillment.model.FulfillmentConfig;
import com.selloohub.leo.fulfillment.model.FulfillmentType;
import com.selloohub.leo.fulfillment.model.PickupLocation;
import com.selloohub.leo.fulfillment.model.PickupLocationStatus;
import com.selloohub.leo.fulfillment.repository.FulfillmentConfigRepository;
import com.selloohub.leo.fulfillment.repository.PickupLocationRepository;
import com.selloohub.leo.order.dto.CheckoutItem;
import com.selloohub.leo.order.dto.CheckoutRequest;
import com.selloohub.leo.order.dto.CheckoutResponse;
import com.selloohub.leo.order.model.*;
import com.selloohub.leo.order.repository.OrderRepository;
import com.selloohub.leo.product.model.Product;
import com.selloohub.leo.product.model.ProductStatus;
import com.selloohub.leo.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckoutService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final PickupLocationRepository pickupLocationRepository;
    private final FulfillmentConfigRepository fulfillmentConfigRepository;
    private final AgentRepository agentRepository;
    private final OrderNumberGenerator orderNumberGenerator;

    public CheckoutService(OrderRepository orderRepository, ProductRepository productRepository, PickupLocationRepository pickupLocationRepository, FulfillmentConfigRepository fulfillmentConfigRepository, AgentRepository agentRepository, OrderNumberGenerator orderNumberGenerator) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.pickupLocationRepository = pickupLocationRepository;
        this.fulfillmentConfigRepository = fulfillmentConfigRepository;
        this.agentRepository = agentRepository;
        this.orderNumberGenerator = orderNumberGenerator;
    }

    //TODO Risk of Data Corruption, do we need @Transactional?
    public CheckoutResponse checkout(CheckoutRequest request, String refCode) {
        List<OrderLine> lines = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CheckoutItem item : request.items()) {
            Product product = productRepository.findActiveByIdAndStatus(item.productId(), ProductStatus.ACTIVE)
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + item.productId()));

            //TODO need to fix concurrency issue
            if (product.getStockQty() < item.qty()) {
                throw new ConflictException("INSUFFICIENT_STOCK", "Insufficient stock for " + product.getSku());
            }

            BigDecimal unitPrice = product.getRetailPrice();
            BigDecimal lineTotal = MoneyMath.round2(unitPrice.multiply(BigDecimal.valueOf(item.qty())));

            //TODO use DDD instead of relying on database IDs(leaking entity ID into domain)
            lines.add(new OrderLine(product.getId(), product.getSku(), product.getName(), OrderLineType.SIMPLE, item.qty(), unitPrice, PriceSource.RETAIL, null, lineTotal, null, null));
            subtotal = subtotal.add(lineTotal);
        }

        subtotal = MoneyMath.round2(subtotal);

        PickupLocation location = pickupLocationRepository.findActiveByIdAndStatus(request.pickupLocationId(), PickupLocationStatus.ACTIVE)
                .orElseThrow(() -> new ResourceNotFoundException("Pickup location not found: " + request.pickupLocationId()));

        FulfillmentConfig config = fulfillmentConfigRepository.findByType(FulfillmentType.PICKUP)
                .filter(FulfillmentConfig::isEnabled)
                .orElseThrow(() -> new ResourceNotFoundException("PICKUP fulfillment is not available"));

        //TODO use DDD instead of relying on database IDs(leaking entity ID into domain)
        FulfillmentBlock fulfillment = new FulfillmentBlock(FulfillmentType.PICKUP, location.getId(), null, null, null, config.getFee());

        BigDecimal grandTotal = MoneyMath.round2(subtotal.add(config.getFee()));

        ReferralBlock referral = resolveReferral(refCode);
        OrderChannel channel = referral.getAgentId() != null ? OrderChannel.CUSTOMER_REFERRAL : OrderChannel.CUSTOMER_DIRECT;

        Order order = new Order(
                orderNumberGenerator.generate(),
                channel,
                request.customerName(),
                PhoneNormalizer.normalize(request.customerPhone()),
                lines,
                subtotal,
                config.getFee(),
                grandTotal,
                fulfillment,
                referral,
                null,
                null,
                Instant.now().plus(24, ChronoUnit.HOURS)
        );
        orderRepository.save(order);
        return CheckoutResponse.from(order);

    }

    private ReferralBlock resolveReferral(String refCode) {
        if (refCode == null || refCode.isBlank()) {
            return new ReferralBlock(null, null, null);
        }

        return agentRepository.findByReferralCode(refCode)
                .filter(a -> a.getStatus() == AgentStatus.ACTIVE)
                .map(a -> new ReferralBlock(a.getId(), refCode, Instant.now()))
                .orElseGet(() -> new ReferralBlock(null, null, null));
    }
}
