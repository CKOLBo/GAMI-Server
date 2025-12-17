package com.team.cklob.gami.domain.postlike.repository;

import com.team.cklob.gami.domain.postlike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMemberEmailAndPost_Id(String memberEmail, Long postId);

    Optional<PostLike> findByMemberEmailAndPost_Id(String memberEmail, Long postId);
}
