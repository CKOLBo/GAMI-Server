package com.team.cklob.gami.domain.member.service;

import com.team.cklob.gami.domain.member.entity.constant.Major;
import com.team.cklob.gami.domain.member.dto.response.GetMemberProfileResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface GetMemberListService {
    Page<GetMemberProfileResponse> execute(
            Major major,
            String name,
            Integer generation,
            Pageable pageable
    );
}
