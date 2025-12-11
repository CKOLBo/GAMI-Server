package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;

public interface PostUpdateService {

    void update(Long postId, PostUpdateRequest request);
}
