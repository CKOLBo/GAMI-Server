package com.team.cklob.gami.domain.chat.service;

import com.team.cklob.gami.domain.chat.presentation.request.ChatMessageRequest;

import java.security.Principal;

public interface ChatMessageService {
    void execute(Long roomId, ChatMessageRequest request, Principal principal);
}
