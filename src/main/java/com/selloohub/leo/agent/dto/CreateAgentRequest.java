package com.selloohub.leo.agent.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateAgentRequest(

        @NotBlank String tierId,
        @NotBlank String name,
        @NotBlank String phone,
        @NotBlank @Email String email,
        @NotBlank String username
) {
}
