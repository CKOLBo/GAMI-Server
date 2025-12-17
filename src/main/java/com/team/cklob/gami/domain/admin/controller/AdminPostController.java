package com.team.cklob.gami.domain.admin.controller;

import com.team.cklob.gami.domain.admin.service.AdminPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/post")
@PreAuthorize("hasAuthority('ROLE_ROLE_ADMIN')")
@Tag(name = "Admin - Post", description = "관리자 게시글 관리 API")
@SecurityRequirement(name = "Bearer")
public class AdminPostController {

    private final AdminPostService adminPostService;

    @Operation(
            summary = "관리자 게시글 삭제",
            description = "관리자 권한으로 특정 게시글을 강제 삭제한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "403", description = "관리자 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음", content = @Content)
    })
    @DeleteMapping("/{postId}")
    public void deletePost(
            @Parameter(description = "게시글 ID", example = "1")
            @PathVariable Long postId
    ) {
        adminPostService.deletePost(postId);
    }
}
