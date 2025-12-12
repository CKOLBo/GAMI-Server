package com.team.cklob.gami.domain.chat.service;

import com.team.cklob.gami.domain.chat.dto.response.ChatMessagePageResponse;

public interface GetChatRoomMessageService {
    ChatMessagePageResponse execute(Long roomId, Long cursorId);
}
