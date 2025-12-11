package com.team.cklob.gami.domain.post.controller;

import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostSearchRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.dto.response.PostResponse;
import com.team.cklob.gami.domain.post.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {

    private final PostCreateService postCreateService;
    private final PostDeleteService postDeleteService;
    private final PostDetailService postDetailService;
    private final PostListService postListService;
    private final PostUpdateService postUpdateService;

    @PostMapping
    public ResponseEntity<Long> createPost(@RequestBody PostCreateRequest request) {
        Long id = postCreateService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(id);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostDetail(@PathVariable("postId") Long postId) {
        PostResponse post = postDetailService.get(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> getPostList(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) String sort
    ) {
        PostSearchRequest request = PostSearchRequest.builder()
                .keyword(keyword)
                .page(page)
                .size(size)
                .sort(sort)
                .build();

        Page<PostResponse> result = postListService.getList(request);
        return ResponseEntity.ok(result);
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Void> updatePost(
            @PathVariable("postId") Long postId,
            @RequestBody PostUpdateRequest request
    ) {
        postUpdateService.update(postId, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("postId") Long postId) {
        postDeleteService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
