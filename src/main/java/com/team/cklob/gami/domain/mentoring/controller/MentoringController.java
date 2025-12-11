package com.team.cklob.gami.domain.mentoring.controller;

import com.team.cklob.gami.domain.member.entity.constant.Major;
import com.team.cklob.gami.domain.mentoring.dto.request.ApplyStatusRequest;
import com.team.cklob.gami.domain.mentoring.dto.response.GetMentorResponse;
import com.team.cklob.gami.domain.mentoring.dto.response.GetReceivedApplyResponse;
import com.team.cklob.gami.domain.mentoring.dto.response.GetSentApplyResponse;
import com.team.cklob.gami.domain.mentoring.dto.response.MentoringApplyResponse;
import com.team.cklob.gami.domain.mentoring.service.*;
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
public class MentoringController {

    private final MentoringApplyService mentoringApplyService;
    private final GetSentApplyService getSentApplyService;
    private final GetReceivedApplyService getReceivedApplyService;
    private final GetMentorListService  getMentorListService;
    private final RandomSearchService randomSearchService;
    private final MentoringApplyStatusService mentoringApplyStatusService;

    @PostMapping("/apply/{mentorId}")
    public ResponseEntity<MentoringApplyResponse> apply(@PathVariable Long mentorId) {
        MentoringApplyResponse response = mentoringApplyService.execute(mentorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/apply/sent")
    public ResponseEntity<List<GetSentApplyResponse>> getSentApply() {
        List<GetSentApplyResponse> response = getSentApplyService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/apply/received")
    public ResponseEntity<List<GetReceivedApplyResponse>> getReceivedApply() {
        List<GetReceivedApplyResponse> response = getReceivedApplyService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/mentor/all")
    public ResponseEntity<Page<GetMentorResponse>> getAllMentor(
            @RequestParam(name = "major", required = false) Major major,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "generation", required = false) Integer generation,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<GetMentorResponse> responses = getMentorListService.execute(
                major, name, generation, pageable
        );

        return ResponseEntity.ok(responses);
    }

    @GetMapping("/random")
    public ResponseEntity<GetMentorResponse> getRandomMentor() {
        GetMentorResponse response = randomSearchService.execute();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/apply/{id}")
    public ResponseEntity<Void> applyStatus(@RequestBody ApplyStatusRequest request, @PathVariable("id") Long applyId) {
        mentoringApplyStatusService.execute(request, applyId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
