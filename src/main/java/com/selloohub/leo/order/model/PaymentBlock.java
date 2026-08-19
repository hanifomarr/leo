package com.selloohub.leo.order.model;

import org.springframework.data.mongodb.core.index.Indexed;

import java.time.Instant;

public class PaymentBlock {

    private String gateway;
    @Indexed
    private String billCode;
    private String paymentUrl;
    private Instant paidAt;
    private String gatewayRef;

    public PaymentBlock(String gateway, String billCode, String paymentUrl) {
        this.gateway = gateway;
        this.billCode = billCode;
        this.paymentUrl = paymentUrl;
    }

    public String getGateway() {
        return gateway;
    }

    public String getBillCode() {
        return billCode;
    }

    public String getPaymentUrl() {
        return paymentUrl;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public void setPaidAt(Instant paidAt) {
        this.paidAt = paidAt;
    }

    public String getGatewayRef() {
        return gatewayRef;
    }

    public void setGatewayRef(String gatewayRef) {
        this.gatewayRef = gatewayRef;
    }
}
