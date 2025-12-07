package com.team.cklob.gami.domain.chat.service.impl;

import com.team.cklob.gami.domain.chat.entity.ChatMessage;
import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatMemberException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatRoomException;
import com.team.cklob.gami.domain.chat.presentation.request.ChatMessageRequest;
import com.team.cklob.gami.domain.chat.presentation.response.ChatMessageResponse;
import com.team.cklob.gami.domain.chat.repository.ChatMessageRepository;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.ChatMessageService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final MemberUtil memberUtil;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @Transactional
    public ChatMessageResponse execute(ChatMessageRequest request) {
        Member sender = memberUtil.getCurrentMember();

        ChatRoom chatRoom = chatRoomRepository.findById(request.roomId())
                .orElseThrow(NotFoundChatRoomException::new);

        if (chatRoomRepository.existsBySenderIdAndRoomId(sender.getId(), request.roomId())) {
            throw new NotFoundChatMemberException();
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .room(chatRoom)
                .message(request.message())
                .sender(sender)
                .createdAt(LocalDateTime.now())
                .build();

        simpMessagingTemplate.convertAndSend("/topic/room/", chatMessage);

        chatMessageRepository.save(chatMessage);

        return new ChatMessageResponse(
                chatMessage.getId(),
                chatMessage.getRoom().getId(),
                chatMessage.getMessage(),
                chatMessage.getCreatedAt()
        );
    }
}
