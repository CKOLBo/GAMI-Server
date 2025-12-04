package com.team.cklob.gami.domain.mentoring.presentation.dto.response;

import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;

import java.time.LocalDateTime;

public record MentoringApplyResponse(
        Long menteeId,
        Long mentorId,
        ApplyStatus applyStatus,
        LocalDateTime createdAt
) {}
