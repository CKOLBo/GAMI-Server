package com.team.cklob.gami.global.event.handler;

import com.team.cklob.gami.domain.ai.service.CreateSummaryService;
import com.team.cklob.gami.global.event.CreateSummaryEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class CreateSummaryEventListener {

    private final CreateSummaryService createSummaryService;

    @Async("taskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleCreateSummaryEvent(CreateSummaryEvent event) {
        createSummaryService.execute(event);
    }
}
