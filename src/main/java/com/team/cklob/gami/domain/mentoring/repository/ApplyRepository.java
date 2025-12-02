package com.team.cklob.gami.domain.mentoring.repository;

import com.team.cklob.gami.domain.mentoring.entity.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplyRepository extends JpaRepository<Apply, Long> {

    Boolean existsByMenteeIdAndMentorId(Long menteeId, Long mentorId);
}
