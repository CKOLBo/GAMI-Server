package com.team.cklob.gami.domain.chat.service.impl;

import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.entity.constant.MatchStatus;
import com.team.cklob.gami.domain.chat.exception.AlreadyExistChatRoomException;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.CreateChatRoomService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberException;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.exception.ApplyNotFoundException;
import com.team.cklob.gami.domain.mentoring.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CreateChatRoomServiceImpl implements CreateChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ApplyRepository  applyRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void execute(Long applyId, Long menteeId, Long mentorId) {

        if (chatRoomRepository.existsByMenteeIdAndMentorId(menteeId, mentorId)) {
            throw new AlreadyExistChatRoomException();
        }
        Apply apply = applyRepository.findById(applyId)
                .orElseThrow(ApplyNotFoundException::new);

        Member mentee = memberRepository.findById(menteeId)
                .orElseThrow(NotFoundMemberException::new);

        Member mentor = memberRepository.findById(mentorId)
                .orElseThrow(NotFoundMemberException::new);

        ChatRoom chatRoom = ChatRoom.builder()
                .apply(apply)
                .mentor(mentor)
                .mentee(mentee)
                .matchStatus(MatchStatus.IN_CHAT)
                .lastMessage("")
                .createdAt(LocalDateTime.now())
                .build();
        chatRoomRepository.save(chatRoom);
    }
}