package com.team.cklob.gami.domain.comment.service;

import com.team.cklob.gami.domain.comment.dto.request.CommentCreateRequest;
import com.team.cklob.gami.domain.comment.dto.response.CommentResponse;

public interface CommentCommandService {

    CommentResponse createComment(Long postId, CommentCreateRequest request);

    void deleteComment(Long commentId);
}
