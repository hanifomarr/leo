package com.selloohub.leo.order.model;

import java.time.Instant;

public class ReferralBlock {

    private String agentId;
    private String referralCode;
    private Instant capturedAt;

    public ReferralBlock(String agentId, String referralCode, Instant capturedAt) {
        this.agentId = agentId;
        this.referralCode = referralCode;
        this.capturedAt = capturedAt;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public Instant getCapturedAt() {
        return capturedAt;
    }
}
