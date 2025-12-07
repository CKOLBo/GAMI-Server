package com.team.cklob.gami.domain.chat.service;

import com.team.cklob.gami.domain.chat.presentation.response.GetChatRoomListResponse;

import java.util.List;

public interface GetChatRoomListService {
    List<GetChatRoomListResponse> execute();
}
