package com.selloohub.leo.payment.model;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Document(collection = "payment_txn")
public class PaymentTxn {

    @Id
    private String id;
    @Indexed
    private String orderId;
    private String orderNo;
    private String gateway;
    @Indexed
    private String billCode;
    @Indexed(unique = true)
    private String gatewayRef;
    private BigDecimal amount;
    private String statusRaw;
    private PaymentStatus normalized;
    private PaymentSource source;
    private Map<String, String> rawPayload;
    @CreatedDate
    private Instant receivedAt;

    public PaymentTxn(String orderId, String orderNo, String gateway, String billCode, String gatewayRef, BigDecimal amount, String statusRaw, PaymentStatus normalized, PaymentSource source, Map<String, String> rawPayload) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.gateway = gateway;
        this.billCode = billCode;
        this.gatewayRef = gatewayRef;
        this.amount = amount;
        this.statusRaw = statusRaw;
        this.normalized = normalized;
        this.source = source;
        this.rawPayload = rawPayload;
    }

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public String getGateway() {
        return gateway;
    }

    public String getBillCode() {
        return billCode;
    }

    public String getGatewayRef() {
        return gatewayRef;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getStatusRaw() {
        return statusRaw;
    }

    public PaymentStatus getNormalized() {
        return normalized;
    }

    public PaymentSource getSource() {
        return source;
    }

    public Map<String, String> getRawPayload() {
        return rawPayload;
    }

    public Instant getReceivedAt() {
        return receivedAt;
    }
}
