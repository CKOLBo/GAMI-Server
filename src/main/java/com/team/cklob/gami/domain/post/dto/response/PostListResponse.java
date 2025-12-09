package com.team.cklob.gami.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.team.cklob.gami.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResponse {

    private Long id;

    @JsonProperty("noName")
    private String noName;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static PostListResponse from(Post post) {
        return PostListResponse.builder()
                .id(post.getId())
                .noName(post.getMember().getName())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
