package com.team.cklob.gami.domain.auth.dto.request;

public record VerifyCodeRequest(
        String email,
        String code
) {}
