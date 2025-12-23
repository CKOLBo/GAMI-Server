package com.team.cklob.gami.domain.post.dto.response;

import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.entity.PostImage;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private int likeCount;
    private int commentCount;
    private boolean isLiked;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> images;

    public static PostResponse from(
            Post post,
            int commentCount,
            boolean isLiked
    ) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .commentCount(commentCount)
                .isLiked(isLiked)
                .memberId(post.getMember().getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .images(
                        post.getImages().stream()
                                .map(PostImage::getImageUrl)
                                .toList()
                )
                .build();
    }
}
