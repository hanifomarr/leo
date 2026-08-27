package com.selloohub.leo.order.dto;

import com.selloohub.leo.order.model.Order;
import com.selloohub.leo.order.model.OrderStatus;

import java.math.BigDecimal;
import java.time.Instant;

public record CheckoutResponse(
        String orderNo,
        OrderStatus status,
        BigDecimal subtotal,
        BigDecimal fulfillmentFee,
        BigDecimal grandTotal,
        Instant expiresAt,
        String paymentUrl
) {
    public static CheckoutResponse from(Order order) {
        return new CheckoutResponse(
                order.getOrderNo(),
                order.getStatus(),
                order.getSubtotal(),
                order.getFulfillmentFee(),
                order.getGrandTotal(),
                order.getExpiresAt(),
                order.getPayment().getPaymentUrl()
        );
    }
}
