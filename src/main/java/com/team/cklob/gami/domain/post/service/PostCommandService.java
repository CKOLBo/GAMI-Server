package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;

public interface PostCommandService {

    Long createPost(PostCreateRequest request);

    void updatePost(Long postId, PostUpdateRequest request);

    void deletePost(Long postId);
}
