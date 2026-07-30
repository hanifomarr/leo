package com.selloohub.leo.agent.dto;

import com.selloohub.leo.agent.model.TierStatus;
import jakarta.validation.constraints.NotBlank;

public record CreateAgentTierRequest(

        @NotBlank  String name,
        String description,
        TierStatus status
) {
}
