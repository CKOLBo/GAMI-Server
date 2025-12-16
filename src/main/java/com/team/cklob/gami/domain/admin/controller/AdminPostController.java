package com.team.cklob.gami.domain.admin.controller;

import com.team.cklob.gami.domain.admin.service.AdminPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/post")
@PreAuthorize("hasAuthority('ROLE_ROLE_ADMIN')")
public class AdminPostController {

    private final AdminPostService adminPostService;

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId) {
        adminPostService.deletePost(postId);
    }
}
