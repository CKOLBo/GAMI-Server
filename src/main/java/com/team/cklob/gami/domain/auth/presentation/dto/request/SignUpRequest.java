package com.team.cklob.gami.domain.auth.presentation.dto.request;

import com.team.cklob.gami.domain.member.entity.constant.Gender;
import com.team.cklob.gami.domain.member.entity.constant.Major;

public record SignUpRequest(
        String email,
        String password,
        String name,
        Integer generation,
        Gender gender,
        Major major
) {}
