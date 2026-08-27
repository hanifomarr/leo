package com.selloohub.leo.payment.gateway;

import com.selloohub.leo.payment.model.PaymentStatus;

import java.math.BigDecimal;
import java.util.Map;

public record PaymentEvent(
        String billCode,
        String gatewayRef,
        String statusRaw,
        PaymentStatus normalized,
        BigDecimal amount,
        Map<String, String> raw
) {
}
