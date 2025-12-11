package com.team.cklob.gami.domain.chat.dto.request;

public record ChatMessageRequest(
   Long roomId,
   String message
) {}
