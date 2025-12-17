package com.team.cklob.gami.domain.mentoring.service;

import com.team.cklob.gami.domain.mentoring.dto.response.MentoringApplyResponse;

public interface MentoringApplyService {
    MentoringApplyResponse execute(Long mentorId);
}
