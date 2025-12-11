package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.request.PostSearchRequest;
import com.team.cklob.gami.domain.post.dto.response.PostResponse;
import org.springframework.data.domain.Page;

public interface PostListService {

    Page<PostResponse> getList(PostSearchRequest request);
}
