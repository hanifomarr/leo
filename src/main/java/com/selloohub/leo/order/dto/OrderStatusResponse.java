package com.selloohub.leo.order.dto;

import com.selloohub.leo.fulfillment.model.FulfillmentType;
import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record OrderStatusResponse(

        String orderNo,
        OrderStatus status,
        List<OrderLineResponse> lines,
        BigDecimal subtotal,
        BigDecimal fulfillmentFee,
        BigDecimal grandTotal,
        FulfillmentType fulfillmentType,
        Instant expiresAt
) {
    public static OrderStatusResponse from(Order order) {
        List<OrderLineResponse> lines = order.getLines()
                .stream()
                .map(OrderLineResponse::from)
                .toList();

        return new OrderStatusResponse(
                order.getOrderNo(),
                order.getStatus(),
                lines,
                order.getSubtotal(),
                order.getFulfillmentFee(),
                order.getGrandTotal(),
                order.getFulfillment().getType(),
                order.getExpiresAt()
        );
    }
}
