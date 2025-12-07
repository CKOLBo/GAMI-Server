package com.team.cklob.gami.domain.chat.service;

import com.team.cklob.gami.domain.chat.presentation.request.ChatMessageRequest;
import com.team.cklob.gami.domain.chat.presentation.response.ChatMessageResponse;

public interface ChatMessageService {
    ChatMessageResponse execute(ChatMessageRequest request);
}
