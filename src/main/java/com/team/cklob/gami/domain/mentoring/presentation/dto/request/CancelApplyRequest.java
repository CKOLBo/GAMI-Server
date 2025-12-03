package com.team.cklob.gami.domain.mentoring.presentation.dto.request;

import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;

public record CancelApplyRequest(
        ApplyStatus applyStatus
) {}
