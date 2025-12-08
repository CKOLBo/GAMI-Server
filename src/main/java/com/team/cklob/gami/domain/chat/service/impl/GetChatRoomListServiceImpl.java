package com.team.cklob.gami.domain.chat.service.impl;

import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.chat.presentation.response.GetChatRoomListResponse;
import com.team.cklob.gami.domain.chat.repository.ChatRoomRepository;
import com.team.cklob.gami.domain.chat.service.GetChatRoomListService;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.exception.NotFoundMemberDetailException;
import com.team.cklob.gami.domain.member.repository.MemberDetailRepository;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetChatRoomListServiceImpl implements GetChatRoomListService {

    private final ChatRoomRepository chatRoomRepository;
    private final MemberDetailRepository memberDetailRepository;
    private final MemberUtil memberUtil;

    @Override
    @Transactional(readOnly = true)
    public List<GetChatRoomListResponse> execute() {
        Member member = memberUtil.getCurrentMember();

        List<ChatRoom> chatRoomList = chatRoomRepository
                .findAllByMentorIdOrMenteeId(member.getId(), member.getId());

        List<Long> otherMemberIds = chatRoomList.stream()
                .map(chatRoom -> {
                    boolean isMentee = chatRoom.getMentee().getId().equals(member.getId());
                    return isMentee ? chatRoom.getMentor().getId() : chatRoom.getMentee().getId();
                })
                .toList();

        Map<Long, MemberDetail> memberDetailMap = memberDetailRepository.findAllById(otherMemberIds)
                .stream()
                .collect(Collectors.toMap(
                        MemberDetail::getId,
                        detail -> detail
                ));

        return chatRoomList.stream()
                .map(chatRoom -> {
                    boolean isMentee = chatRoom.getMentee().getId().equals(member.getId());
                    Member otherMember = isMentee ? chatRoom.getMentor() : chatRoom.getMentee();

                    MemberDetail otherMemberDetail = memberDetailMap.get(otherMember.getId());
                    if (otherMemberDetail == null) {
                        throw new NotFoundMemberDetailException();
                    }

                    return GetChatRoomListResponse.builder()
                            .id(chatRoom.getId())
                            .name(otherMember.getName())
                            .lastMessage(chatRoom.getLastMessage())
                            .major(otherMemberDetail.getMajor())
                            .generation(otherMemberDetail.getGeneration())
                            .build();
                })
                .toList();
    }
}
