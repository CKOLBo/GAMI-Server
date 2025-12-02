package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberException;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import com.team.cklob.gami.domain.mentoring.exception.AlreadyRegisteredMentorException;
import com.team.cklob.gami.domain.mentoring.exception.SelfApplyNotAllowedException;
import com.team.cklob.gami.domain.mentoring.presentation.dto.response.MentoringApplyResponse;
import com.team.cklob.gami.domain.mentoring.repository.ApplyRepository;
import com.team.cklob.gami.domain.mentoring.service.MentoringApplyService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MentoringApplyServiceImpl implements MentoringApplyService {

    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;

    @Override
    @Transactional
    public MentoringApplyResponse execute(Long mentorId) {
        Member mentee = memberUtil.getCurrentMember();

        Member mentor = memberRepository.findById(mentorId)
                .orElseThrow(NotFoundMemberException::new);

        if (mentor.getId().equals(mentee.getId())) {
            throw new SelfApplyNotAllowedException();
        }

        if (!applyRepository.existsByMenteeIdAndMentorId(mentee.getId(), mentor.getId())) {
            throw new AlreadyRegisteredMentorException();
        }

        // 생생된 채팅 예외도 추가

        Apply apply = Apply.builder()
                .mentee(mentee)
                .mentor(mentor)
                .applyStatus(ApplyStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        applyRepository.save(apply);

        return new MentoringApplyResponse(
                mentee.getId(),
                mentor.getId(),
                ApplyStatus.PENDING,
                LocalDateTime.now()
        );
    }
}
