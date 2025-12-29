package com.team.cklob.gami.domain.mentoring.repository;

import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    Boolean existsByMenteeIdAndMentorIdAndApplyStatus(Long menteeId, Long mentorId, ApplyStatus applyStatus);

    @Query("SELECT a FROM Apply a JOIN FETCH a.mentor WHERE a.mentee.id = :menteeId AND a.applyStatus = :applyStatus ORDER BY a.createdAt DESC")
    List<Apply> findAllByMenteeIdAndApplyStatusWithMentor(@Param("menteeId") Long menteeId, @Param("applyStatus") ApplyStatus applyStatus);

    @Query("SELECT a FROM Apply a JOIN FETCH a.mentee WHERE a.mentor.id = :mentorId AND a.applyStatus = :applyStatus ORDER BY a.createdAt DESC")
    List<Apply> findAllByMentorIdAndApplyStatusWithMentee(@Param("mentorId") Long mentorId, @Param("applyStatus") ApplyStatus applyStatus);}