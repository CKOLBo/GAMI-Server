package com.team.cklob.gami.domain.chat.service.impl;

import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatMemberException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatRoomException;
import com.team.cklob.gami.domain.chat.dto.response.GetChatRoomResponse;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.GetChatRoomService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetChatRoomServiceImpl implements GetChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberUtil  memberUtil;

    @Override
    @Transactional(readOnly = true)
    public GetChatRoomResponse execute(Long roomId) {
        Member member = memberUtil.getCurrentMember();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        if (!chatRoomRepository.existsByRoomIdAndMemberId(chatRoom.getId(), member.getId())) {
            throw new NotFoundChatMemberException();
        }

        MemberDetail otherDetail = chatRoomRepository.findOtherMemberInRoom(chatRoom.getId(), member.getId())
                .orElseThrow(NotFoundChatMemberException::new);

        return new GetChatRoomResponse(
                chatRoom.getId(),
                otherDetail.getMember().getName(),
                otherDetail.getMajor(),
                otherDetail.getGeneration()
        );
    }
}
