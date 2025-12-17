package com.team.cklob.gami.domain.postlike.controller;

import com.team.cklob.gami.domain.postlike.service.PostLikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}/like")
@Tag(name = "Post Like", description = "게시글 좋아요 API")
@SecurityRequirement(name = "Bearer")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(
            summary = "게시글 좋아요",
            description = "게시글에 좋아요를 누른다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "좋아요 처리 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId
    ) {
        postLikeService.like(postId);
    }

    @Operation(
            summary = "게시글 좋아요 취소",
            description = "게시글에 누른 좋아요를 취소한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "좋아요 취소 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId
    ) {
        postLikeService.unlike(postId);
    }
}
