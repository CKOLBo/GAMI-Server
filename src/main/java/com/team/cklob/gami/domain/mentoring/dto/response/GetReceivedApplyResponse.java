package com.team.cklob.gami.domain.mentoring.dto.response;

import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetReceivedApplyResponse(
        Long applyId,
        Long menteeId,
        String name,
        ApplyStatus applyStatus,
        LocalDateTime createdAt
) {}
