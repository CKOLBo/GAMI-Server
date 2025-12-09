package com.team.cklob.gami.domain.mentoring.service.impl;

import com.team.cklob.gami.domain.chat.exception.AlreadyExistChatRoomException;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
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
import java.util.List;

@Service
@RequiredArgsConstructor
public class MentoringApplyServiceImpl implements MentoringApplyService {

    private final MemberUtil memberUtil;
    private final MemberRepository memberRepository;
    private final ApplyRepository applyRepository;
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public MentoringApplyResponse execute(Long mentorId) {
        Member mentee = memberUtil.getCurrentMember();

        Member mentor = memberRepository.findById(mentorId)
                .orElseThrow(NotFoundMemberException::new);

        if (mentor.getId().equals(mentee.getId())) {
            throw new SelfApplyNotAllowedException();
        }

        if (applyRepository.existsByMenteeIdAndMentorIdAndApplyStatusIn(mentee.getId(), mentor.getId(), List.of(ApplyStatus.PENDING, ApplyStatus.ACCEPTED))) {
            throw new AlreadyRegisteredMentorException();
        }

        if (chatRoomRepository.existsByMenteeIdAndMentorId(mentee.getId(), mentorId)) {
            throw new AlreadyExistChatRoomException();
        }

        Apply apply = Apply.builder()
                .mentee(mentee)
                .mentor(mentor)
                .applyStatus(ApplyStatus.PENDING)
                .build();

        Apply savedApply = applyRepository.save(apply);

        return new MentoringApplyResponse(
                savedApply.getMentee().getId(),
                savedApply.getMentor().getId(),
                savedApply.getApplyStatus(),
                savedApply.getCreatedAt()
        );
    }
}
