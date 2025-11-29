package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostSearchRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.dto.response.PostListResponse;
import com.team.cklob.gami.domain.post.dto.response.PostResponse;

import java.util.List;

public interface PostService {

    PostResponse createPost(PostCreateRequest request);

    List<PostListResponse> getPostList(PostSearchRequest request);

    PostResponse getPost(Long id);

    PostResponse updatePost(Long id, PostUpdateRequest request);

    void deletePost(Long id);
}
