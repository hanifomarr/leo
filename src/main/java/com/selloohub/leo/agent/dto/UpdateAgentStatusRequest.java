package com.selloohub.leo.agent.dto;

import com.selloohub.leo.agent.model.AgentStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateAgentStatusRequest(

        @NotNull AgentStatus status
) {
}
