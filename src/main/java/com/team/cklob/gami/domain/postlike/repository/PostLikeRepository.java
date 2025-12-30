package com.team.cklob.gami.domain.postlike.repository;

import com.team.cklob.gami.domain.postlike.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    boolean existsByMemberEmailAndPost_Id(String memberEmail, Long postId);

    Optional<PostLike> findByMemberEmailAndPost_Id(String memberEmail, Long postId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from PostLike pl where pl.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
