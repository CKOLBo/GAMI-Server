package com.team.cklob.gami.domain.mentoring.service;

import com.team.cklob.gami.domain.mentoring.presentation.dto.request.CancelApplyRequest;

public interface CancelApplyService {
    void execute(CancelApplyRequest request, Long applyId);
}
