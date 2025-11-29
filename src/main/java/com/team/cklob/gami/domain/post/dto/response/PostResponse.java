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
public class PostResponse {

    private Long id;

    @JsonProperty("noName")
    private String noName;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    public static PostResponse from(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .noName(post.getMember().getName())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .updateAt(post.getUpdateAt())
                .build();
    }
}
