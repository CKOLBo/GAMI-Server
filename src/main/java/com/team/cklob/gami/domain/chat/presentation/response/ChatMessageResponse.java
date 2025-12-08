package com.team.cklob.gami.domain.chat.presentation.response;

import com.team.cklob.gami.domain.chat.entity.ChatMessage;

import java.time.LocalDateTime;

public record ChatMessageResponse(
        Long messageId,
        String message,
        LocalDateTime createdAt,
        Long senderId,
        String senderName
) {
    public static ChatMessageResponse from(ChatMessage message, Long currentUserId) {
        return new ChatMessageResponse(
                message.getId(),
                message.getMessage(),
                message.getCreatedAt(),
                message.getSender().getId(),
                message.getSender().getName()
        );
    }
}
