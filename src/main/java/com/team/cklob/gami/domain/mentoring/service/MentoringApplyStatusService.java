package com.team.cklob.gami.domain.mentoring.service;

import com.team.cklob.gami.domain.mentoring.presentation.dto.request.ApplyStatusRequest;

public interface MentoringApplyStatusService {
    void execute(ApplyStatusRequest request, Long applyId);
}
