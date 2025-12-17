package com.team.cklob.gami.domain.comment.controller;

import com.team.cklob.gami.domain.comment.dto.request.CommentCreateRequest;
import com.team.cklob.gami.domain.comment.dto.response.CommentResponse;
import com.team.cklob.gami.domain.comment.service.CommentCommandService;
import com.team.cklob.gami.domain.comment.service.CommentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Tag(name = "Comment", description = "댓글 API")
@SecurityRequirement(name = "Bearer")
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @Operation(
            summary = "댓글 작성",
            description = "게시글에 댓글을 작성한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "댓글 작성 성공",
                    content = @Content(schema = @Schema(implementation = CommentResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    @PostMapping("/post/{postId}/comment")
    public ResponseEntity<CommentResponse> createComment(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable("postId") Long postId,
            @Valid @RequestBody CommentCreateRequest request
    ) {
        CommentResponse response = commentCommandService.createComment(postId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "댓글 목록 조회",
            description = "게시글에 작성된 댓글 목록을 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "댓글 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = CommentResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/post/{postId}/comment")
    public ResponseEntity<List<CommentResponse>> getComments(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable("postId") Long postId
    ) {
        List<CommentResponse> responses = commentQueryService.getComments(postId);
        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "댓글 삭제",
            description = "댓글을 삭제한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "댓글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "403", description = "삭제 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/post/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "댓글 ID", example = "1")
            @PathVariable("commentId") Long commentId
    ) {
        commentCommandService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
