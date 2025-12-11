package com.team.cklob.gami.domain.auth.dto.request;

import com.team.cklob.gami.domain.auth.entity.constant.VerificationType;

public record SendCodeRequest(
        String email,
        VerificationType verificationType
) {}
