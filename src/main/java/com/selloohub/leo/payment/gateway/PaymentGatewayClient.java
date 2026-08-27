package com.selloohub.leo.payment.gateway;

import java.util.Map;

public interface PaymentGatewayClient {
    BillRef createBill(BillRequest request);

    PaymentEvent parseWebhook(Map<String, String> rawParams);

    GatewayStatus getStatus(String billCode);
}

