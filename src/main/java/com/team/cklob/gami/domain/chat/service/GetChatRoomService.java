package com.team.cklob.gami.domain.chat.service;

import com.team.cklob.gami.domain.chat.dto.response.GetChatRoomResponse;

public interface GetChatRoomService {
    GetChatRoomResponse execute(Long roomId);
}
