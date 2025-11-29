package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.presentation.dto.request.SendVerificationCodeRequest;
import jakarta.mail.MessagingException;

public interface SendCodeService {
    void execute(SendVerificationCodeRequest request) throws MessagingException;
}
