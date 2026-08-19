package com.selloohub.leo.order.model;

import java.math.BigDecimal;

public class CommissionBlock {

    private String agentId;
    private String tierId;
    private String configId;
    private String rateType;
    private BigDecimal rateValue;
    private BigDecimal amount;
    private CommissionStatus status;

    public CommissionBlock(String agentId, String tierId, String configId, String rateType, BigDecimal rateValue, BigDecimal amount, CommissionStatus status) {
        this.agentId = agentId;
        this.tierId = tierId;
        this.configId = configId;
        this.rateType = rateType;
        this.rateValue = rateValue;
        this.amount = amount;
        this.status = status;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getTierId() {
        return tierId;
    }

    public String getConfigId() {
        return configId;
    }

    public String getRateType() {
        return rateType;
    }

    public BigDecimal getRateValue() {
        return rateValue;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public CommissionStatus getStatus() {
        return status;
    }

    public void setStatus(CommissionStatus status) {
        this.status = status;
    }
}
