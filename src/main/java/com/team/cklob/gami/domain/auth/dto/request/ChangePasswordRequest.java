package com.team.cklob.gami.domain.auth.dto.request;

public record ChangePasswordRequest(
        String email,
        String newPassword
) {}
