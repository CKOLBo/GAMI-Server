package com.team.cklob.gami.domain.chat.service.impl;

import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.entity.constant.MessageType;
import com.team.cklob.gami.domain.chat.exception.AlreadyEndedChatRoomException;
import com.team.cklob.gami.domain.chat.exception.AlreadyLeftChatRoomException;
import com.team.cklob.gami.domain.chat.exception.NotChatRoomMemberException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatRoomException;
import com.team.cklob.gami.domain.chat.presentation.response.ChatSystemMessageResponse;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.LeaveChatRoomService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LeaveChatRoomServiceImpl implements LeaveChatRoomService {

    private final MemberUtil memberUtil;
    private final ChatRoomRepository chatRoomRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public void execute(Long roomId) {
        Member currentMember = memberUtil.getCurrentMember();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        if (!chatRoom.isActive()) {
            throw new AlreadyEndedChatRoomException();
        }

        if (chatRoom.hasLeft(currentMember)) {
            throw new AlreadyLeftChatRoomException();
        }

        if (!chatRoom.isMember(currentMember)) {
            throw new NotChatRoomMemberException();
        }

        chatRoom.leave(currentMember);

        String memberName = currentMember.getName();
        boolean isMentor = currentMember.getId().equals(chatRoom.getMentor().getId());
        String role = isMentor ? "멘토" : "멘티";

        if (chatRoom.isActive()) {
            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId,
                    ChatSystemMessageResponse.builder()
                            .type(MessageType.USER_LEFT)
                            .content(role + " " + memberName + "님이 나가셨습니다.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        } else {
            messagingTemplate.convertAndSend(
                    "/topic/room/" + roomId,
                    ChatSystemMessageResponse.builder()
                            .type(MessageType.ROOM_ENDED)
                            .content("채팅방이 종료되었습니다.")
                            .timestamp(LocalDateTime.now())
                            .build()
            );
        }
    }
}