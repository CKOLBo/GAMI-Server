package com.team.cklob.gami.domain.chat.repository;

import com.team.cklob.gami.domain.chat.entity.ChatRoom;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    boolean existsByMenteeIdAndMentorId(Long menteeId, Long mentorId);

    @Query("SELECT CASE WHEN COUNT(cr) > 0 THEN true ELSE false END FROM ChatRoom cr " +
            "WHERE cr.id = :roomId AND (cr.mentor.id = :memberId OR cr.mentee.id = :memberId)")
    boolean existsByRoomIdAndMemberId(
            @Param("roomId") Long roomId,      // 순서 변경!
            @Param("memberId") Long memberId
    );
    @Query("""
    select md
    from ChatRoom cr
    join MemberDetail md
      on (cr.mentor.id = :memberId and md.member = cr.mentee)
      or (cr.mentee.id = :memberId and md.member = cr.mentor)
    where cr.id = :roomId
    """)
    Optional<MemberDetail> findOtherMemberInRoom(@Param("roomId") Long roomId,
                                                 @Param("memberId") Long memberId);

    List<ChatRoom> findAllByMentorIdOrMenteeId(Long mentorId, Long menteeId);
}
