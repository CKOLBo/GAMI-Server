package com.team.cklob.gami.domain.member.service;

import com.team.cklob.gami.domain.member.presentation.dto.response.GetMemberProfileResponse;

public interface GetMemberProfileService {
    GetMemberProfileResponse execute(Long memberId);
}
