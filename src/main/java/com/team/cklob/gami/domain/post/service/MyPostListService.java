package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.response.PostListResponse;

import java.util.List;

public interface MyPostListService {

    List<PostListResponse> getMyPosts();
}
