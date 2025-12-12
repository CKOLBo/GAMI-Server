package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.dto.request.SignUpRequest;

public interface SignUpService {
    void execute(SignUpRequest request);
}
