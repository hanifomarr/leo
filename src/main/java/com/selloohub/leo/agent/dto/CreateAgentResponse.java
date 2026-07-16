package com.selloohub.leo.agent.dto;

import com.selloohub.leo.agent.model.Agent;
import com.selloohub.leo.agent.model.AgentStatus;

public record CreateAgentResponse(

        String id,
        String username,
        String name,
        String phone,
        String email,
        String referralCode,
        AgentStatus status,
        String initialPassword
) {
    public static CreateAgentResponse from(Agent agent, String initialPassword) {
        return new CreateAgentResponse(
                agent.getId(),
                agent.getUsername(),
                agent.getName(),
                agent.getPhone(),
                agent.getEmail(),
                agent.getReferralCode(),
                agent.getStatus(),
                initialPassword
        );
    }


}
