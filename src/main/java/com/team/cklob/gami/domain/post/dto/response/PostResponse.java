package com.team.cklob.gami.domain.post.dto.response;

import com.team.cklob.gami.domain.post.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private int likeCount;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likeCount(post.getLikeCount())
                .memberId(post.getMember().getId())
                .createdAt(post.getCreatedAt())
                .updateAt(post.getUpdateAt())
                .build();
    }
}
