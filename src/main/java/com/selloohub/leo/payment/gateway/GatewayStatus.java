package com.selloohub.leo.payment.gateway;

import com.selloohub.leo.payment.model.PaymentStatus;

import java.math.BigDecimal;

public record GatewayStatus(
        PaymentStatus normalized,
        String gatewayRef,
        BigDecimal amount
) {
}
