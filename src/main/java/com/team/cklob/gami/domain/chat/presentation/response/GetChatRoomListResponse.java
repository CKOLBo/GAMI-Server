package com.team.cklob.gami.domain.chat.presentation.response;

import com.team.cklob.gami.domain.member.entity.constant.Major;
import lombok.Builder;

@Builder
public record GetChatRoomListResponse(
        Long id,
        String name,
        String lastMessage,
        Major major,
        Integer generation
) {}
