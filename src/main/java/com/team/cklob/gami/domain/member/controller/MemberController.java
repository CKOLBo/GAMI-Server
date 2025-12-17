package com.team.cklob.gami.domain.member.controller;

import com.team.cklob.gami.domain.member.entity.constant.Major;
import com.team.cklob.gami.domain.member.dto.request.ResetPasswordRequest;
import com.team.cklob.gami.domain.member.dto.request.PatchMajorRequest;
import com.team.cklob.gami.domain.member.dto.response.GetMemberProfileResponse;
import com.team.cklob.gami.domain.member.service.*;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
@Tag(name = "Member", description = "회원 정보 API")
@SecurityRequirement(name = "Bearer")
public class MemberController {

    private final ResetPasswordService resetPasswordService;
    private final GetMemberProfileService getMemberProfileService;
    private final GetMyProfileService getMyProfileService;
    private final PatchMajorService patchMajorService;
    private final GetMemberListService getMemberListService;

    @Operation(
            summary = "회원 프로필 조회",
            description = "회원 ID로 특정 회원의 프로필을 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원 프로필 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetMemberProfileResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "회원을 찾을 수 없음", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetMemberProfileResponse> getMemberProfile(
            @Parameter(description = "회원 ID", example = "1")
            @PathVariable("id") Long memberId
    ) {
        GetMemberProfileResponse response = getMemberProfileService.execute(memberId);
        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "내 프로필 조회",
            description = "현재 로그인한 사용자의 프로필을 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "내 프로필 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetMemberProfileResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @GetMapping
    public ResponseEntity<GetMemberProfileResponse> getMyProfile() {
        return ResponseEntity.ok(getMyProfileService.execute());
    }

    @Operation(
            summary = "회원 목록 조회",
            description = "전공, 이름, 기수 조건으로 회원 목록을 조회한다 (페이징)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "회원 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetMemberProfileResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @GetMapping("/all")
    public ResponseEntity<Page<GetMemberProfileResponse>> getAllMemberProfile(
            @Parameter(description = "전공", example = "BACKEND")
            @RequestParam(name = "major", required = false) Major major,

            @Parameter(description = "이름", example = "홍길동")
            @RequestParam(name = "name", required = false) String name,

            @Parameter(description = "기수", example = "8")
            @RequestParam(name = "generation", required = false) Integer generation,

            @Parameter(description = "페이지 번호 (0부터 시작)", example = "0")
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,

            @Parameter(description = "페이지 크기", example = "10")
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<GetMemberProfileResponse> responses = getMemberListService.execute(
                major, name, generation, pageable
        );

        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "비밀번호 변경",
            description = "현재 로그인한 사용자의 비밀번호를 변경한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody ResetPasswordRequest request
    ) {
        resetPasswordService.execute(request);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "전공 수정",
            description = "현재 로그인한 사용자의 전공 정보를 수정한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "전공 수정 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @PatchMapping("/major")
    public ResponseEntity<Void> patchMajor(
            @RequestBody PatchMajorRequest request
    ) {
        patchMajorService.execute(request);
        return ResponseEntity.ok().build();
    }
}
