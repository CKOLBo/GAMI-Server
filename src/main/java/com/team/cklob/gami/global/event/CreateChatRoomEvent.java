package com.team.cklob.gami.global.event;

public record CreateChatRoomEvent(
   Long menteeId,
   Long mentorId,
   Long applyId
) {}
