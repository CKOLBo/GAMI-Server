package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberDetailException;
import com.team.cklob.gami.domain.member.repository.MemberDetailRepository;
import com.team.cklob.gami.domain.mentoring.exception.NotFoundRandomMentorException;
import com.team.cklob.gami.domain.mentoring.presentation.dto.response.GetMentorResponse;
import com.team.cklob.gami.domain.mentoring.service.RandomSearchService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RandomSearchServiceImpl implements RandomSearchService {

    private final MemberUtil memberUtil;
    private final MemberDetailRepository memberDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public GetMentorResponse execute() {
        Member mentee = memberUtil.getCurrentMember();

        MemberDetail details = memberDetailRepository.findById(mentee.getId())
                .orElseThrow(NotFoundMemberDetailException::new);

        MemberDetail mentorDetail = memberDetailRepository
                .findRandomByMajorAndGenerationLessThanEqual(details.getMajor(), details.getGeneration())
                .orElseThrow(NotFoundRandomMentorException::new);

        return new GetMentorResponse(
                mentorDetail.getId(),
                mentorDetail.getMember().getName(),
                mentorDetail.getGender(),
                mentorDetail.getGeneration(),
                mentorDetail.getMajor()
        );
    }
}
