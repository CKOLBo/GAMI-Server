package com.team.cklob.gami.domain.chat.service.impl;

import com.team.cklob.gami.domain.chat.entity.ChatMessage;
import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.exception.NotChatRoomMemberException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatMemberException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatRoomException;
import com.team.cklob.gami.domain.chat.presentation.response.ChatMessagePageResponse;
import com.team.cklob.gami.domain.chat.presentation.response.ChatMessageResponse;
import com.team.cklob.gami.domain.chat.repository.ChatMessageRepository;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.GetChatRoomMessageService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetChatRoomMessageServiceImpl implements GetChatRoomMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberUtil memberUtil;

    private static final int PAGE_SIZE = 30;

    @Override
    @Transactional(readOnly = true)
    public ChatMessagePageResponse execute(Long roomId, Long cursorId) {
        Member currentMember = memberUtil.getCurrentMember();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        if (!chatRoomRepository.existsByRoomIdAndMemberId(currentMember.getId(), roomId)) {
            throw new NotFoundChatMemberException();
        }

        List<ChatMessage> messages;

        if (cursorId == null) {
            messages = chatMessageRepository.findLatestMessages(
                    roomId,
                    PageRequest.of(0, PAGE_SIZE)
            );
        } else {
            messages = chatMessageRepository.findPreviousMessages(
                    roomId,
                    cursorId,
                    PageRequest.of(0, PAGE_SIZE)
            );
        }

        Collections.reverse(messages);

        Long nextCursor = messages.isEmpty() ? null : messages.get(0).getId();
        boolean hasMore = messages.size() == PAGE_SIZE;

        List<ChatMessageResponse> responses = messages.stream()
                .map(msg -> new ChatMessageResponse(
                        msg.getId(),
                        msg.getMessage(),
                        msg.getCreatedAt(),
                        msg.getSender().getId(),
                        msg.getSender().getName()
                ))
                .toList();
        return ChatMessagePageResponse.builder()
                .roomId(roomId)
                .messages(responses)
                .nextCursor(nextCursor)
                .hasMore(hasMore)
                .roomStatus(chatRoom.getRoomStatus())
                .currentMemberLeft(chatRoom.hasLeft(currentMember))
                .build();
    }
}
