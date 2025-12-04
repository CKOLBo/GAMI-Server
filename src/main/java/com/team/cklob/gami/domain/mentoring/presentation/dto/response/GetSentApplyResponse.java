package com.team.cklob.gami.domain.mentoring.presentation.dto.response;

import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record GetSentApplyResponse(
   Long applyId,
   Long mentorId,
   String name,
   ApplyStatus applyStatus,
   LocalDateTime createdAt
) {}
