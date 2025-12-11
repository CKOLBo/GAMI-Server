package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.dto.request.SignInRequest;
import com.team.cklob.gami.domain.auth.dto.responnse.TokenResponse;

public interface SignInService {
    TokenResponse execute(SignInRequest request);
}
