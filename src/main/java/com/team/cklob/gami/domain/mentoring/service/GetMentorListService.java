package com.team.cklob.gami.domain.mentoring.service;

import com.team.cklob.gami.domain.member.entity.constant.Major;
import com.team.cklob.gami.domain.mentoring.dto.response.GetMentorResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GetMentorListService {
    Page<GetMentorResponse> execute(
            Major major,
            String name,
            Integer generation,
            Pageable pageable
    );
}
