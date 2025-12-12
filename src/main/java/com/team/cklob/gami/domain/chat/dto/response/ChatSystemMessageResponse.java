package com.team.cklob.gami.domain.chat.dto.response;

import com.team.cklob.gami.domain.chat.entity.constant.MessageType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatSystemMessageResponse(
        MessageType type,
        String content,
        LocalDateTime timestamp
) {
}
