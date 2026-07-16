package com.selloohub.leo.agent.dto;

public record RegenerateReferralCodeResponse(

        String agentId,
        String username,
        String newReferralCode
) {
}
