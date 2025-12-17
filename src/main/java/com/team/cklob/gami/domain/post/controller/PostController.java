package com.team.cklob.gami.domain.post.controller;

import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostSearchRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.dto.response.PostResponse;
import com.team.cklob.gami.domain.post.dto.response.PostSummaryResponse;
import com.team.cklob.gami.domain.post.service.*;
import com.team.cklob.gami.global.auth.MemberDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
@Tag(name = "Post", description = "게시글 관련 API")
public class PostController {

    private final PostCreateService postCreateService;
    private final PostDeleteService postDeleteService;
    private final PostDetailService postDetailService;
    private final PostListService postListService;
    private final PostUpdateService postUpdateService;
    private final PostSummaryService postSummaryService;

    @Operation(
            summary = "게시글 작성",
            description = "새로운 게시글을 작성한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @SecurityRequirement(name = "Bearer")
    @PostMapping
    public ResponseEntity<Long> createPost(
            @RequestBody PostCreateRequest request,
            @AuthenticationPrincipal MemberDetails memberDetails
    ) {
        Long memberId = memberDetails.getMember().getId();
        Long id = postCreateService.create(request, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @Operation(
            summary = "게시글 상세 조회",
            description = "게시글 ID로 게시글 상세 정보를 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 조회 성공",
                    content = @Content(schema = @Schema(implementation = PostResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable("postId") Long postId
    ) {
        return ResponseEntity.ok(postDetailService.get(postId));
    }

    @Operation(
            summary = "게시글 요약 조회",
            description = "게시글의 요약 정보(좋아요 수, 댓글 수 등)를 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 요약 조회 성공",
                    content = @Content(schema = @Schema(implementation = PostSummaryResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/summary/{postId}")
    public ResponseEntity<PostSummaryResponse> getPostSummary(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable("postId") Long postId
    ) {
        PostSummaryResponse response = postSummaryService.execute(postId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "게시글 목록 조회",
            description = "게시글 목록을 페이징, 검색, 정렬 조건으로 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시글 목록 조회 성공"
            )
    })
    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPostList(
            @Parameter(description = "검색 키워드", example = "스프링")
            @RequestParam(required = false) String keyword,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(required = false) Integer page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(required = false) Integer size,

            @Parameter(description = "정렬 기준", example = "createdAt,desc")
            @RequestParam(required = false) String sort
    ) {
        PostSearchRequest request = PostSearchRequest.builder()
                .keyword(keyword)
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        return ResponseEntity.ok(postListService.getList(request));
    }

    @Operation(
            summary = "게시글 수정",
            description = "게시글 내용을 수정한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content)
    })
    @SecurityRequirement(name = "Bearer")
    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        postUpdateService.update(postId, request);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "게시글 삭제",
            description = "게시글을 삭제한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "403", description = "권한 없음", content = @Content)
    })
    @SecurityRequirement(name = "Bearer")
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable("postId") Long postId
    ) {
        postDeleteService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
