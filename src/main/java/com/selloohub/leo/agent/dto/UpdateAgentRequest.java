package com.selloohub.leo.agent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UpdateAgentRequest(
        @NotBlank String tierId,
        @NotBlank String name,
        @NotBlank @Email String email
) {
}
