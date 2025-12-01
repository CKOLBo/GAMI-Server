package com.team.cklob.gami.domain.member.presentation.dto.request;

import com.team.cklob.gami.domain.member.entity.constant.Major;

public record PatchMajorRequest(
        Major major
) {}
