package com.team.cklob.gami.domain.comment.controller;

import com.team.cklob.gami.domain.comment.dto.request.CommentCreateRequest;
import com.team.cklob.gami.domain.comment.dto.response.CommentResponse;
import com.team.cklob.gami.domain.comment.service.CommentCommandService;
import com.team.cklob.gami.domain.comment.service.CommentQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable("postId") Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentCommandService.createComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<List<CommentResponse>> getComments(
            @PathVariable("postId") Long postId
    ) {
        List<CommentResponse> responses = commentQueryService.getComments(postId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/post/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("commentId") Long commentId
    ) {
        commentCommandService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
