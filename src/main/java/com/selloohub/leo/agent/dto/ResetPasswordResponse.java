package com.selloohub.leo.agent.dto;

public record ResetPasswordResponse(
        String agentId,
        String username,
        String newPassword
) {

}
