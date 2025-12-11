package com.team.cklob.gami.domain.member.dto.request;

public record ResetPasswordRequest(
        String password,
        String newPassword
) {}
