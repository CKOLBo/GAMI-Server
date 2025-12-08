package com.team.cklob.gami.domain.chat.service;

public interface CreateChatRoomService {
    void execute(Long applyId, Long menteeId, Long mentorId);
}
