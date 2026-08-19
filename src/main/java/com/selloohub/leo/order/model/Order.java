package com.selloohub.leo.order.model;

import com.selloohub.leo.common.audit.Auditable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "order")
@CompoundIndex(name = "status_expiresAt_idx", def = "{'status': 1, 'expiresAt': 1}")
@CompoundIndex(name = "referralAgent_createdAt_idx", def = "{'referral.agentId': 1, 'createdAt': 1}")
public class Order extends Auditable {

    @Id
    private String id;
    @Indexed(unique = true)
    private String orderNo;
    private OrderChannel channel;

    private String customerName;
    @Indexed
    private String customerPhone;

    private List<OrderLine> lines;

    private BigDecimal subtotal;
    private BigDecimal fulfillmentFee;
    private BigDecimal grandTotal;

    private FulfillmentBlock fulfillment;
    private ReferralBlock referral;

    @Indexed
    private String campaignId;
    private String campaignSlug;

    private PaymentBlock payment;
    private CommissionBlock commission;

    private OrderStatus status;
    private boolean fulfillmentHold;
    private String holdReason;

    private Instant expiresAt;

    public Order(String orderNo, OrderChannel channel, String customerName, String customerPhone, List<OrderLine> lines, BigDecimal subtotal, BigDecimal fulfillmentFee, BigDecimal grandTotal, FulfillmentBlock fulfillment, ReferralBlock referral, String campaignId, String campaignSlug, Instant expiresAt) {
        this.orderNo = orderNo;
        this.channel = channel;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.lines = lines;
        this.subtotal = subtotal;
        this.fulfillmentFee = fulfillmentFee;
        this.grandTotal = grandTotal;
        this.fulfillment = fulfillment;
        this.referral = referral;
        this.campaignId = campaignId;
        this.campaignSlug = campaignSlug;
        this.expiresAt = expiresAt;
        this.status = OrderStatus.PENDING_PAYMENT;
        this.fulfillmentHold = false;
    }

    public String getId() {
        return id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public OrderChannel getChannel() {
        return channel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public List<OrderLine> getLines() {
        return lines;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getFulfillmentFee() {
        return fulfillmentFee;
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }

    public FulfillmentBlock getFulfillment() {
        return fulfillment;
    }

    public ReferralBlock getReferral() {
        return referral;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getCampaignSlug() {
        return campaignSlug;
    }

    public PaymentBlock getPayment() {
        return payment;
    }

    public void setPayment(PaymentBlock payment) {
        this.payment = payment;
    }

    public CommissionBlock getCommission() {
        return commission;
    }

    public void setCommission(CommissionBlock commission) {
        this.commission = commission;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public boolean isFulfillmentHold() {
        return fulfillmentHold;
    }

    public void setFulfillmentHold(boolean fulfillmentHold) {
        this.fulfillmentHold = fulfillmentHold;
    }

    public String getHoldReason() {
        return holdReason;
    }

    public void setHoldReason(String holdReason) {
        this.holdReason = holdReason;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }
}
