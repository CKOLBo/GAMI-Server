package com.team.cklob.gami.domain.chat.dto.response;

import com.team.cklob.gami.domain.member.entity.constant.Major;

public record GetChatRoomResponse(
   Long roomId,
   String name,
   Major major,
   Integer generation
) {}
