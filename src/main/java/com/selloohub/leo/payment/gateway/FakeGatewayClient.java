package com.selloohub.leo.payment.gateway;

import com.selloohub.leo.payment.model.PaymentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Profile("dev")
public class FakeGatewayClient implements PaymentGatewayClient {

    // in-memory only
    private final Map<String, BillRequest> issuedBills = new ConcurrentHashMap<>();
    private final PaymentStatus fakeOutcome;

    public FakeGatewayClient(@Value("${app.payment.fake.outcome}") PaymentStatus fakeOutcome) {
        this.fakeOutcome = fakeOutcome;
    }

    @Override
    public BillRef createBill(BillRequest request) {
        String billCode = "FAKE-" + UUID.randomUUID();
        issuedBills.put(billCode, request);
        String paymentUrl = "http://localhost:8080/fake-pay/" + billCode;
        return new BillRef("FAKE", billCode, paymentUrl);
    }

    @Override
    public PaymentEvent parseWebhook(Map<String, String> rawParams) {

        throw new UnsupportedOperationException("FakeGatewayClient does not receive real webhooks");
    }

    @Override
    public GatewayStatus getStatus(String billCode) {
        BillRequest request = issuedBills.get(billCode);
        if (request == null) {
            throw new IllegalStateException("Unknown fake billCode: " + billCode);
        }

        String gatewayRef = "FAKE-REF-" + billCode;
        return new GatewayStatus(fakeOutcome, gatewayRef, request.amount());
    }
}
