package com.team.cklob.gami.domain.post.repository;

import com.team.cklob.gami.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    @Query("""
        select p
        from Post p
        left join fetch p.images
        where p.id = :postId
    """)
    Optional<Post> findPostDetail(@Param("postId") Long postId);

    @Query("""
        select count(c)
        from Comment c
        where c.post.id = :postId
    """)
    int countComments(@Param("postId") Long postId);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount + 1 where p.id = :postId")
    void increaseLike(@Param("postId") Long postId);

    @Modifying
    @Query("update Post p set p.likeCount = p.likeCount - 1 where p.id = :postId and p.likeCount > 0")
    void decreaseLike(@Param("postId") Long postId);

    List<Post> findAllByMemberId(Long memberId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query(value = "delete from `like` where post_id = :postId", nativeQuery = true)
    void deleteAllFromLikeTableByPostId(@Param("postId") Long postId);
}
