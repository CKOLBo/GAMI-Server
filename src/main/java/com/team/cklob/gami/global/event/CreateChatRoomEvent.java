package com.team.cklob.gami.global.event;

public record CreateChatRoomEvent(
   Long menteeId,
   Long mentorId,
   Long applyId
) {
    public CreateChatRoomEvent(Long menteeId, Long mentorId, Long applyId) {
        this.menteeId = menteeId;
        this.mentorId = mentorId;
        this.applyId = applyId;
    }
}
