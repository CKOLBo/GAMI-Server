package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.presentation.dto.request.ChangePasswordRequest;

public interface ChangePasswordService {
    void execute(ChangePasswordRequest request);
}
