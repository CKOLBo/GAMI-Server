package com.team.cklob.gami.domain.chat.dto.response;

import com.team.cklob.gami.domain.chat.entity.constant.RoomStatus;
import lombok.Builder;

import java.util.List;

@Builder
public record ChatMessagePageResponse(
        Long roomId,
        List<ChatMessageResponse> messages,
        Long nextCursor,
        boolean hasMore,
        RoomStatus roomStatus,
        boolean currentMemberLeft
) {
}
