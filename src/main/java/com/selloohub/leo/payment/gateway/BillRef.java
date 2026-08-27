package com.selloohub.leo.payment.gateway;

public record BillRef(
        String gateway,
        String billCode,
        String paymentUrl
) {
}

