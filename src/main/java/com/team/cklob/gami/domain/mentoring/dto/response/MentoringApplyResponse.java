package com.team.cklob.gami.domain.mentoring.dto.response;

import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MentoringApplyResponse(
        Long applyId,
        Long menteeId,
        Long mentorId,
        ApplyStatus applyStatus,
        LocalDateTime createdAt
) {
    public static MentoringApplyResponse from(Apply apply) {
        return MentoringApplyResponse.builder()
                .applyId(apply.getId())
                .menteeId(apply.getMentee().getId())
                .mentorId(apply.getMentor().getId())
                .createdAt(apply.getCreatedAt())
                .applyStatus(apply.getApplyStatus())
                .build();
    }
}
