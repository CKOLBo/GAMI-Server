package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.dto.response.TokenResponse;

public interface ReissueTokenService {
    TokenResponse execute(String refreshToken);
}
