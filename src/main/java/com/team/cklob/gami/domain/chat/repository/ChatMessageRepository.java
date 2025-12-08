package com.team.cklob.gami.domain.chat.repository;

import com.team.cklob.gami.domain.chat.entity.ChatMessage;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    @Query("SELECT cm FROM ChatMessage cm " +
            "JOIN FETCH cm.sender " +
            "WHERE cm.room.id = :roomId " +
            "AND cm.id < :cursorId " +
            "ORDER BY cm.id DESC")
    List<ChatMessage> findPreviousMessages(
            @Param("roomId") Long roomId,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );

    @Query("SELECT cm FROM ChatMessage cm " +
            "JOIN FETCH cm.sender " +
            "WHERE cm.room.id = :roomId " +
            "ORDER BY cm.id DESC")
    List<ChatMessage> findLatestMessages(
            @Param("roomId") Long roomId,
            Pageable pageable
    );
}
