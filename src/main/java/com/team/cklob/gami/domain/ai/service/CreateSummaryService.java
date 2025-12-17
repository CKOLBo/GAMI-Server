package com.team.cklob.gami.domain.ai.service;

import com.team.cklob.gami.global.event.CreateSummaryEvent;

public interface CreateSummaryService {
    void execute(CreateSummaryEvent event);
}
