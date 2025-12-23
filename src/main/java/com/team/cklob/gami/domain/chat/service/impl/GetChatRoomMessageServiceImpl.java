package com.team.cklob.gami.domain.chat.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.cklob.gami.domain.chat.entity.ChatMessage;
import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatMemberException;
import com.team.cklob.gami.domain.chat.exception.NotFoundChatRoomException;
import com.team.cklob.gami.domain.chat.dto.response.ChatMessagePageResponse;
import com.team.cklob.gami.domain.chat.dto.response.ChatMessageResponse;
import com.team.cklob.gami.domain.chat.repository.ChatMessageRepository;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.GetChatRoomMessageService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetChatRoomMessageServiceImpl implements GetChatRoomMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final MemberUtil memberUtil;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> stringRedisTemplate;

    private static final int PAGE_SIZE = 30;
    private static final String CHAT_MESSAGES_PREFIX = "CHAT:MESSAGES:";

    @Override
    @Transactional(readOnly = true)
    public ChatMessagePageResponse execute(Long roomId, Long cursorId) {
        Member currentMember = memberUtil.getCurrentMember();

        ChatRoom chatRoom = chatRoomRepository.findById(roomId)
                .orElseThrow(NotFoundChatRoomException::new);

        if (!chatRoomRepository.existsByRoomIdAndMemberId(currentMember.getId(), roomId)) {
            throw new NotFoundChatMemberException();
        }

        List<ChatMessageResponse> responses;

        if (cursorId == null) {
            responses = getLatestMessagesFromCache(roomId);
        } else {
            List<ChatMessage> messages = chatMessageRepository.findPreviousMessages(
                    roomId,
                    cursorId,
                    PageRequest.of(0, PAGE_SIZE)
            );
            responses = convertToResponses(messages);
        }

        responses = new ArrayList<>(responses);
        Collections.reverse(responses);

        Long nextCursor = responses.isEmpty() ? null : responses.get(0).messageId();
        boolean hasMore = responses.size() == PAGE_SIZE;

        return ChatMessagePageResponse.builder()
                .roomId(roomId)
                .messages(responses)
                .nextCursor(nextCursor)
                .hasMore(hasMore)
                .roomStatus(chatRoom.getRoomStatus())
                .currentMemberLeft(chatRoom.hasLeft(currentMember))
                .build();
    }

    private List<ChatMessageResponse> getLatestMessagesFromCache(Long roomId) {
        try {
            String key = CHAT_MESSAGES_PREFIX + roomId;
            List<String> cachedJson = stringRedisTemplate.opsForList()
                    .range(key, 0, PAGE_SIZE - 1);

            if (cachedJson != null && !cachedJson.isEmpty()) {
                List<ChatMessageResponse> cached = cachedJson.stream()
                        .map(this::safeParseCachedMessage)
                        .filter(res -> res != null)
                        .toList();

                return new ArrayList<>(cached.size() >= PAGE_SIZE ? cached.subList(0, PAGE_SIZE) : cached);
            }
        } catch (Exception e) {
            log.error("Failed to get messages from cache for room: {}", roomId, e);
        }

        List<ChatMessage> messages = chatMessageRepository.findLatestMessages(
                roomId,
                PageRequest.of(0, PAGE_SIZE)
        );
        return convertToResponses(messages);
    }

    private ChatMessageResponse safeParseCachedMessage(String raw) {
        try {
            String json = unwrapIfQuotedJson(raw);
            return objectMapper.readValue(json, ChatMessageResponse.class);
        } catch (Exception e) {
            log.error("Failed to deserialize chat message from cache. raw: {}", raw, e);
            return null;
        }
    }

    private String unwrapIfQuotedJson(String raw) {
        if (raw == null) return null;
        String s = raw.trim();

        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            try {
                return objectMapper.readValue(s, String.class);
            } catch (Exception ignore) {
                return s;
            }
        }
        return s;
    }

    private List<ChatMessageResponse> convertToResponses(List<ChatMessage> messages) {
        List<ChatMessageResponse> list = messages.stream()
                .map(msg -> new ChatMessageResponse(
                        msg.getId(),
                        msg.getMessage(),
                        msg.getCreatedAt(),
                        msg.getSender().getId(),
                        msg.getSender().getName()
                ))
                .toList();

        return new ArrayList<>(list);
    }
}
