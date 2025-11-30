package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.presentation.dto.request.SignUpRequest;

public interface SignUpService {
    void execute(SignUpRequest request);
}
