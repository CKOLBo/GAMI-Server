package com.team.cklob.gami.domain.auth.presentation.dto.responnse;

import java.time.LocalDateTime;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        LocalDateTime accessTokenExpiresIn,
        LocalDateTime refreshTokenExpiresIn
) {}
