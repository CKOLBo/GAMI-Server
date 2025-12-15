package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.response.PostSummaryResponse;

public interface PostSummaryService {
    PostSummaryResponse execute(Long postId);
}
