package com.team.cklob.gami.domain.auth.service;

import com.team.cklob.gami.domain.auth.presentation.dto.request.SendCodeRequest;
import jakarta.mail.MessagingException;

public interface SendCodeService {
    void execute(SendCodeRequest request) throws MessagingException;
}
