package com.team.cklob.gami.domain.postlike.controller;

import com.team.cklob.gami.domain.postlike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/{postId}/like")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void like(@PathVariable Long postId) {
        postLikeService.like(postId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unlike(@PathVariable Long postId) {
        postLikeService.unlike(postId);
    }
}
