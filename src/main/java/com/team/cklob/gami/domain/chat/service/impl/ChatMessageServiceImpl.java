package com.team.cklob.gami.domain.chat.service.impl;

import com.team.cklob.gami.domain.chat.entity.ChatMessage;
import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.exception.CannotSendMessageAfterLeavingException;
import com.team.cklob.gami.domain.chat.exception.CannotSendMessageToEndedRoomException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatMemberException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatRoomException;
import com.team.cklob.gami.domain.chat.presentation.request.ChatMessageRequest;
import com.team.cklob.gami.domain.chat.presentation.response.ChatMessageResponse;
import com.team.cklob.gami.domain.chat.repository.ChatMessageRepository;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.ChatMessageService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberException;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional
    public void execute(Long roomId, ChatMessageRequest request, Principal principal) {
        String email = principal.getName();

        Member sender = memberRepository.findByEmail(email)
                .orElseThrow(NotFoundMemberException::new);

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        boolean isMember = chatRoomRepository.existsByRoomIdAndMemberId(sender.getId(), roomId);

        if (!isMember) {
            throw new NotFoundChatMemberException();
        }

        if (!chatRoom.isActive()) {
            throw new CannotSendMessageToEndedRoomException();
        }

        if (chatRoom.hasLeft(sender)) {
            throw new CannotSendMessageAfterLeavingException();
        }

        ChatMessage message = ChatMessage.builder()
                .room(chatRoom)
                .message(request.message())
                .sender(sender)
                .build();

        chatMessageRepository.save(message);
        chatRoom.updateLastMessage(request.message());

        messagingTemplate.convertAndSend(
                "/topic/room/" + roomId,
                new ChatMessageResponse(
                        message.getId(),
                        message.getMessage(),
                        message.getCreatedAt(),
                        sender.getId(),
                        sender.getName()
                )
        );
    }
}
