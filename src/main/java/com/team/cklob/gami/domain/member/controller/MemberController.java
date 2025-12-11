package com.team.cklob.gami.domain.member.controller;

import com.team.cklob.gami.domain.member.entity.constant.Major;
import com.team.cklob.gami.domain.member.dto.request.ResetPasswordRequest;
import com.team.cklob.gami.domain.member.dto.request.PatchMajorRequest;
import com.team.cklob.gami.domain.member.dto.response.GetMemberProfileResponse;
import com.team.cklob.gami.domain.member.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final ResetPasswordService resetPasswordService;
    private final GetMemberProfileService getMemberProfileService;
    private final GetMyProfileService getMyProfileService;
    private final PatchMajorService patchMajorService;
    private final GetMemberListService getMemberListService;

    @GetMapping("/{id}")
    public ResponseEntity<GetMemberProfileResponse> getMemberProfile(@PathVariable("id") Long memberId) {
        GetMemberProfileResponse response = getMemberProfileService.execute(memberId);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<GetMemberProfileResponse> getMyProfile() {
        return ResponseEntity.ok(getMyProfileService.execute());
    }

    @GetMapping("/all")
    public ResponseEntity<Page<GetMemberProfileResponse>> getAllMemberProfile(
            @RequestParam(name = "major", required = false) Major major,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "generation", required = false) Integer generation,
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<GetMemberProfileResponse> responses = getMemberListService.execute(
                major, name, generation, pageable
        );

        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(@RequestBody ResetPasswordRequest request) {
        resetPasswordService.execute(request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/major")
    public ResponseEntity<Void> patchMajor(@RequestBody PatchMajorRequest request) {
        patchMajorService.execute(request);
        return ResponseEntity.ok().build();
    }
}
