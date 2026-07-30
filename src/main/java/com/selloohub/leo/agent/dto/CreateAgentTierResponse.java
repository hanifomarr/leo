package com.selloohub.leo.agent.dto;

import com.selloohub.leo.agent.model.AgentTier;
import com.selloohub.leo.agent.model.TierStatus;

public record CreateAgentTierResponse(

        String id,
        String name,
        String description,
        TierStatus status
) {

    public static CreateAgentTierResponse from(AgentTier agentTier) {
        return new CreateAgentTierResponse(
                agentTier.getId(),
                agentTier.getName(),
                agentTier.getDescription(),
                agentTier.getStatus()
        );
    }
}
