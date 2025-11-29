package com.team.cklob.gami.domain.member.repository;

import com.team.cklob.gami.domain.member.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDetailRepository extends JpaRepository<MemberDetail, Long> {
    Optional<MemberDetail> findById(Long id);
}
