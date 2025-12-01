package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.presentation.dto.request.VerifyCodeRequest;

public interface VerifyCodeService {
    void execute(VerifyCodeRequest request);
}
