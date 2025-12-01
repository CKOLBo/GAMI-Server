package com.team.cklob.gami.domain.member.presentation.dto.request;

public record ResetPasswordRequest(
        String password,
        String newPassword
) {}
