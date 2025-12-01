package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.presentation.dto.request.SignInRequest;
import com.team.cklob.gami.domain.auth.presentation.dto.responnse.TokenResponse;

public interface SignInService {
    TokenResponse execute(SignInRequest request);
}
