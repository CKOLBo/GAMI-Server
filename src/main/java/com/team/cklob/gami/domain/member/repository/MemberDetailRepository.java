package com.team.cklob.gami.domain.member.repository;

import com.team.cklob.gami.global.auth.MemberDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDetailRepository extends JpaRepository<MemberDetails, Long> {
    Optional<MemberDetails> findById(Long id);
}
