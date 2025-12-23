package com.team.cklob.gami.domain.post.controller;

import com.team.cklob.gami.domain.post.dto.response.PostListResponse;
import com.team.cklob.gami.domain.post.service.MyPostListService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mypost")
public class MyPostController {

    private final MyPostListService myPostListService;

    @GetMapping
    public List<PostListResponse> getMyPosts(
    ) {
        return myPostListService.getMyPosts();
    }
}
