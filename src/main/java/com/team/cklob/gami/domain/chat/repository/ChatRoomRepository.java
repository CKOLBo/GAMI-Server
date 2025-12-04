package com.team.cklob.gami.domain.chat.repository;

import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByMenteeIdAndMentorId(Long menteeId, Long mentorId);

    List<ChatRoom> findByMenteeIdAndMentorId(Long menteeId, Long mentorId);
}
