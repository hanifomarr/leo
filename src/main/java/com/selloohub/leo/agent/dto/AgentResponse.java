package com.selloohub.leo.agent.dto;

import com.selloohub.leo.agent.model.Agent;
import com.selloohub.leo.agent.model.AgentStatus;

public record AgentResponse(

        String id,
        String tierId,
        String name,
        String phone,
        String email,
        String username,
        String referralCode,
        AgentStatus status
) {

    public static AgentResponse from(Agent agent) {
        return new AgentResponse(
                agent.getId(),
                agent.getTierId(),
                agent.getName(),
                agent.getPhone(),
                agent.getEmail(),
                agent.getUsername(),
                agent.getReferralCode(),
                agent.getStatus()
        );
    }

}
