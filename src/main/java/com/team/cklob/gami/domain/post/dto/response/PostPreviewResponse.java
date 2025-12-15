package com.team.cklob.gami.domain.post.dto.response;

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
public class PostPreviewResponse {

    private Long id;

    private String title;

    private LocalDateTime createdAt;

    public static PostPreviewResponse from(Post post) {
        return PostPreviewResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
