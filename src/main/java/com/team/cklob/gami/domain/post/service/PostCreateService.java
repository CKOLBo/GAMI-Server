package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;

public interface PostCreateService {
    Long create(PostCreateRequest request, Long memberId);
}