package com.team.cklob.gami.domain.member.repository;

import com.team.cklob.gami.domain.chat.entity.constant.RoomStatus;
import com.team.cklob.gami.domain.member.entity.MemberDetail;
import com.team.cklob.gami.domain.member.entity.constant.Major;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {
    Optional<MemberDetail> findById(Long id);

    @Query(
            value = "SELECT md FROM MemberDetail md JOIN md.member m " +
                    "WHERE (:major IS NULL OR md.major = :major) " +
                    "AND (:name IS NULL OR m.name LIKE %:name%) " +
                    "AND (:generation IS NULL OR md.generation = :generation)",
            countQuery = "SELECT COUNT(md) FROM MemberDetail md JOIN md.member m " +
                    "WHERE (:major IS NULL OR md.major = :major) " +
                    "AND (:name IS NULL OR m.name LIKE %:name%) " +
                    "AND (:generation IS NULL OR md.generation = :generation)"
    )
    Page<MemberDetail> findAllWithFilters(
            @Param("major") Major major,
            @Param("name") String name,
            @Param("generation") Integer generation,
            Pageable pageable
    );

    @Query("""
            SELECT md
            FROM MemberDetail md
            JOIN md.member m
            WHERE
              (:major IS NULL OR md.major = :major)
              AND (:name IS NULL OR m.name LIKE %:name%)
              AND (:generation IS NULL OR md.generation < :generation)
              AND m.id <> :memberId
              AND NOT EXISTS (
                SELECT 1
                FROM ChatRoom r
                WHERE r.roomStatus = :roomStatus
                  AND (
                    (r.mentor.id = :memberId AND r.mentee.id = m.id)
                    OR
                    (r.mentor.id = m.id AND r.mentee.id = :memberId)
                  )
              )
            """)
    Page<MemberDetail> findAllWithFiltersIncludingSeniorsExcludeActiveRoom(
            @Param("major") Major major,
            @Param("name") String name,
            @Param("generation") Integer generation,
            @Param("memberId") Long memberId,
            @Param("roomStatus") RoomStatus roomStatus,
            Pageable pageable
    );


    @Query(value = "SELECT * FROM member_detail md " +
            "WHERE md.major = :#{#major.name()} " +
            "AND md.generation < :generation " +
            "ORDER BY RAND() " +
            "LIMIT 1",
            nativeQuery = true)
    Optional<MemberDetail> findRandomByMajorAndGenerationLessThan(
            @Param("major") Major major,
            @Param("generation") Integer generation
    );
}
