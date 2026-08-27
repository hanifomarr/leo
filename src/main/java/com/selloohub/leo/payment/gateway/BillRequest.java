package com.selloohub.leo.payment.gateway;

import java.math.BigDecimal;

public record BillRequest(
        String orderNo,
        BigDecimal amount
) {
}
