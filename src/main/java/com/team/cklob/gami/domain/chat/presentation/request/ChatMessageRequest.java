package com.team.cklob.gami.domain.chat.presentation.request;

public record ChatMessageRequest(
   Long roomId,
   String message
) {}
