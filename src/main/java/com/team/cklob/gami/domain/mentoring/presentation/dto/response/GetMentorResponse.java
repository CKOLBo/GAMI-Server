package com.team.cklob.gami.domain.mentoring.presentation.dto.response;

import com.team.cklob.gami.domain.member.entity.constant.Gender;
import com.team.cklob.gami.domain.member.entity.constant.Major;
import lombok.Builder;

@Builder
public record GetMentorResponse(
        Long  memberId,
        String name,
        Gender gender,
        Integer generation,
        Major major
) {}
