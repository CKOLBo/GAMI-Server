package com.team.cklob.gami.domain.post.repository;

import com.team.cklob.gami.domain.post.entity.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostImageRepository extends JpaRepository<PostImage, Long> {

    List<PostImage> findAllByPostId(Long postId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("delete from PostImage pi where pi.post.id = :postId")
    void deleteAllByPostId(@Param("postId") Long postId);
}
