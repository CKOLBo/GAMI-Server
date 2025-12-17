package com.team.cklob.gami.domain.mentoring.controller;

import com.team.cklob.gami.domain.member.entity.constant.Major;
import com.team.cklob.gami.domain.mentoring.dto.request.ApplyStatusRequest;
import com.team.cklob.gami.domain.mentoring.dto.response.GetMentorResponse;
import com.team.cklob.gami.domain.mentoring.dto.response.GetReceivedApplyResponse;
import com.team.cklob.gami.domain.mentoring.dto.response.GetSentApplyResponse;
import com.team.cklob.gami.domain.mentoring.dto.response.MentoringApplyResponse;
import com.team.cklob.gami.domain.mentoring.service.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mentoring")
@RequiredArgsConstructor
@Tag(name = "Mentoring", description = "멘토링 신청 및 조회 API")
@SecurityRequirement(name = "Bearer")
public class MentoringController {

    private final MentoringApplyService mentoringApplyService;
    private final GetSentApplyService getSentApplyService;
    private final GetReceivedApplyService getReceivedApplyService;
    private final GetMentorListService getMentorListService;
    private final RandomSearchService randomSearchService;
    private final MentoringApplyStatusService mentoringApplyStatusService;

    @Operation(
            summary = "멘토링 신청",
            description = "특정 멘토에게 멘토링을 신청한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "멘토링 신청 성공",
                    content = @Content(schema = @Schema(implementation = MentoringApplyResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "멘토를 찾을 수 없음", content = @Content)
    })
    @PostMapping("/apply/{mentorId}")
    public ResponseEntity<MentoringApplyResponse> apply(
            @Parameter(description = "멘토 ID", example = "1")
            @PathVariable Long mentorId
    ) {
        MentoringApplyResponse response = mentoringApplyService.execute(mentorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "보낸 멘토링 신청 목록 조회",
            description = "내가 보낸 멘토링 신청 목록을 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "보낸 신청 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetSentApplyResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @GetMapping("/apply/sent")
    public ResponseEntity<List<GetSentApplyResponse>> getSentApply() {
        List<GetSentApplyResponse> response = getSentApplyService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "받은 멘토링 신청 목록 조회",
            description = "내가 받은 멘토링 신청 목록을 조회한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "받은 신청 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetReceivedApplyResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @GetMapping("/apply/received")
    public ResponseEntity<List<GetReceivedApplyResponse>> getReceivedApply() {
        List<GetReceivedApplyResponse> response = getReceivedApplyService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "멘토 목록 조회",
            description = "전공, 이름, 기수 조건으로 멘토 목록을 조회한다 (페이징)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "멘토 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetMentorResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @GetMapping("/mentor/all")
    public ResponseEntity<Page<GetMentorResponse>> getAllMentor(
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

        Page<GetMentorResponse> responses = getMentorListService.execute(
                major, name, generation, pageable
        );

        return ResponseEntity.ok(responses);
    }

    @Operation(
            summary = "랜덤 멘토 추천",
            description = "조건에 맞는 멘토 한 명을 랜덤으로 추천한다"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "랜덤 멘토 조회 성공",
                    content = @Content(schema = @Schema(implementation = GetMentorResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content)
    })
    @GetMapping("/random")
    public ResponseEntity<GetMentorResponse> getRandomMentor() {
        GetMentorResponse response = randomSearchService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
            summary = "멘토링 신청 상태 변경",
            description = "멘토링 신청을 승인 또는 거절한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "멘토링 신청 상태 변경 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "403", description = "처리 권한 없음", content = @Content),
            @ApiResponse(responseCode = "404", description = "멘토링 신청을 찾을 수 없음", content = @Content)
    })
    @PatchMapping("/apply/{id}")
    public ResponseEntity<Void> applyStatus(
            @Parameter(description = "멘토링 신청 ID", example = "1")
            @PathVariable("id") Long applyId,
            @RequestBody ApplyStatusRequest request
    ) {
        mentoringApplyStatusService.execute(request, applyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
