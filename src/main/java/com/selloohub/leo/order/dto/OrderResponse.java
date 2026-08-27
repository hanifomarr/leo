package com.selloohub.leo.order.dto;

import com.selloohub.leo.fulfillment.model.FulfillmentType;
import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderResponse(
        String id,
        String orderNo,
        OrderStatus status,
        boolean fulfillmentHold,
        String holdReason,
        List<OrderLineResponse> lines,

        BigDecimal subtotal,
        BigDecimal fulfillmentFee,
        BigDecimal grandTotal,

        FulfillmentType fulfillmentType,
        Instant expiresAt
) {

    public static OrderResponse from(Order order) {

        List<OrderLineResponse> lines = order.getLines()
                .stream()
                .map(OrderLineResponse::from)
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getOrderNo(),
                order.getStatus(),
                order.isFulfillmentHold(),
                order.getHoldReason(),
                lines,
                order.getSubtotal(),
                order.getFulfillmentFee(),
                order.getGrandTotal(),
                order.getFulfillment().getType(),
                order.getExpiresAt()
        );
    }
}
