package com.team.cklob.gami.global.event.handler;

import com.team.cklob.gami.domain.chat.service.CreateChatRoomService;
import com.team.cklob.gami.global.event.CreateChatRoomEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CreateChatRoomEventListener {

    private final CreateChatRoomService createChatRoomService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    public void handleCreateChatRoomEvent(CreateChatRoomEvent event) {
        createChatRoomService.execute(event.applyId(), event.menteeId(), event.mentorId());
    }
}
