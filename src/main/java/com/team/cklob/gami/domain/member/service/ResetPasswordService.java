package com.team.cklob.gami.domain.member.service;

import com.team.cklob.gami.domain.member.presentation.dto.request.ResetPasswordRequest;

public interface ResetPasswordService {
    void execute(ResetPasswordRequest request);
}
