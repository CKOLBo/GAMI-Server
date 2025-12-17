package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.dto.request.SendCodeRequest;

public interface SendCodeService {
    void execute(SendCodeRequest request);
}
