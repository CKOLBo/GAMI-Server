package com.team.cklob.gami.domain.auth.presentation.dto.request;

public record ChangePasswordRequest(
        String email,
        String code,
        String newPassword
) {}
