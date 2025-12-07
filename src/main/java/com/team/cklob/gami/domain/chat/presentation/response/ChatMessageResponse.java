package com.team.cklob.gami.domain.chat.presentation.response;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long messageId,
        Long roomId,
        String message,
        LocalDateTime createdAt
) {
}
