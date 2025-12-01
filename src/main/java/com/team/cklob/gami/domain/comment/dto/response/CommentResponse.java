package com.team.cklob.gami.domain.comment.dto.response;

import com.team.cklob.gami.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CommentResponse {

    private Long postId;
    private Long commentId;
    private String comment;
    private LocalDateTime createdAt;

    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
                .postId(comment.getPost().getId())
                .commentId(comment.getId())
                .comment(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
