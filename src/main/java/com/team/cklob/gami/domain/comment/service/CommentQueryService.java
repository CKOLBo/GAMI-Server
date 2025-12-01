package com.team.cklob.gami.domain.comment.service;

import com.team.cklob.gami.domain.comment.dto.response.CommentResponse;

import java.util.List;

public interface CommentQueryService {

    List<CommentResponse> getComments(Long postId);
}
