package com.team.cklob.gami.domain.mentoring.repository;

import com.team.cklob.gami.domain.mentoring.entity.Apply;
import com.team.cklob.gami.domain.mentoring.entity.constant.ApplyStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    Boolean existsByMenteeIdAndMentorIdAndApplyStatusIn(Long menteeId, Long mentorId, List<ApplyStatus> applyStatus);

    List<Apply> findAllByMenteeIdAndApplyStatusOrderByCreatedAtDesc(Long menteeId, ApplyStatus applyStatus);
    List<Apply> findAllByMentorIdAndApplyStatusOrderByCreatedAtDesc(Long mentorId, ApplyStatus applyStatus);
}
