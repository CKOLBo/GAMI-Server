package com.team.cklob.gami.domain.mentoring.service;

import com.team.cklob.gami.domain.mentoring.dto.request.ApplyStatusRequest;

public interface MentoringApplyStatusService {
    void execute(ApplyStatusRequest request, Long applyId);
}
