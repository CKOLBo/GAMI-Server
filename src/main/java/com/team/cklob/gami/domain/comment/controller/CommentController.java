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
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @PostMapping
    public ResponseEntity<CommentResponse> createComment(
            @RequestParam("postId") Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentCommandService.createComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getComments(
            @RequestParam("postId") Long postId
    ) {
        List<CommentResponse> responses = commentQueryService.getComments(postId);
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("id") Long commentId
    ) {
        commentCommandService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}