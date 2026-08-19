package com.selloohub.leo.order.dto;

import com.selloohub.leo.order.model.OrderLine;

import java.math.BigDecimal;

public record OrderLineResponse(
        String skuSnapshot,
        String nameSnapshot,
        int qty,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
    public static OrderLineResponse from(OrderLine orderLine) {
        return new OrderLineResponse(
                orderLine.getSkuSnapshot(),
                orderLine.getNameSnapshot(),
                orderLine.getQty(),
                orderLine.getUnitPrice(),
                orderLine.getLineTotal()
        );
    }
}
