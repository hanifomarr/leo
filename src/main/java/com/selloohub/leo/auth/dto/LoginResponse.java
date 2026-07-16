package com.selloohub.leo.auth.dto;

public record LoginResponse(
        String token,
        String role,
        String displayName
) {
}
