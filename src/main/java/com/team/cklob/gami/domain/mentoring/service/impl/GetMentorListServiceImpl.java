package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.entity.constant.Major;
import com.team.cklob.gami.domain.member.repository.MemberDetailRepository;
import com.team.cklob.gami.domain.mentoring.dto.response.GetMentorResponse;
import com.team.cklob.gami.domain.mentoring.service.GetMentorListService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetMentorListServiceImpl implements GetMentorListService {

    private final MemberDetailRepository memberDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<GetMentorResponse> execute(
            Major major,
            String name,
            Integer generation,
            Pageable pageable) {

        Page<MemberDetail> detailsPage = memberDetailRepository.findAllWithFiltersIncludingSeniors(
                major, name, generation, pageable
        );

        return detailsPage.map(detail -> {
            Member m = detail.getMember();
            return GetMentorResponse.builder()
                    .memberId(m.getId())
                    .name(m.getName())
                    .gender(detail.getGender())
                    .generation(detail.getGeneration())
                    .major(detail.getMajor())
                    .build();
        });
    }
}
