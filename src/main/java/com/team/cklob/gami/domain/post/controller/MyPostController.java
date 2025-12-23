package com.team.cklob.gami.domain.post.controller;

import com.team.cklob.gami.domain.post.dto.response.PostListResponse;
import com.team.cklob.gami.domain.post.service.MyPostListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "MyPost", description = "내 게시글 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypost")
public class MyPostController {

    private final MyPostListService myPostListService;

    @Operation(
            summary = "내 게시글 목록 조회",
            description = "로그인한 사용자가 작성한 게시글 목록을 조회합니다.",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내 게시글 조회 성공",
                    content = @Content(schema = @Schema(implementation = PostListResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류"
            )
    })
    @GetMapping
    public List<PostListResponse> getMyPosts() {
        return myPostListService.getMyPosts();
    }
}
