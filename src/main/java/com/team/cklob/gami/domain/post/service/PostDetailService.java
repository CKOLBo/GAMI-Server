package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.response.PostResponse;

public interface PostDetailService {

    PostResponse get(Long postId);
}
