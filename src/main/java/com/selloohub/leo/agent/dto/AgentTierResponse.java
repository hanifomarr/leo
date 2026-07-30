package com.selloohub.leo.agent.dto;

import com.selloohub.leo.agent.model.AgentTier;
import com.selloohub.leo.agent.model.TierStatus;

public record AgentTierResponse(

        String id,
        String name,
        String description,
        TierStatus status
) {

    public static AgentTierResponse from(AgentTier agentTier) {
        return new AgentTierResponse(
                agentTier.getId(),
                agentTier.getName(),
                agentTier.getDescription(),
                agentTier.getStatus()
        );
    }
}
