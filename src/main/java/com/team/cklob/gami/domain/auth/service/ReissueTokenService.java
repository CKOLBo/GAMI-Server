package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.presentation.dto.responnse.TokenResponse;

public interface ReissueTokenService {
    TokenResponse execute(String refreshToken);
}
