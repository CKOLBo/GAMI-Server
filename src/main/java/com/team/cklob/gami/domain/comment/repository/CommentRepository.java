package com.team.cklob.gami.domain.comment.repository;

import com.team.cklob.gami.domain.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from Comment c where c.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
