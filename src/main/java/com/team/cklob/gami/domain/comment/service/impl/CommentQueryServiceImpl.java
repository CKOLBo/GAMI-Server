package com.team.cklob.gami.domain.comment.service.impl;

import com.team.cklob.gami.domain.comment.dto.response.CommentResponse;
import com.team.cklob.gami.domain.comment.entity.Comment;
import com.team.cklob.gami.domain.comment.repository.CommentRepository;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.comment.service.CommentQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentQueryServiceImpl implements CommentQueryService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public List<CommentResponse> getComments(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);

        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }
}
