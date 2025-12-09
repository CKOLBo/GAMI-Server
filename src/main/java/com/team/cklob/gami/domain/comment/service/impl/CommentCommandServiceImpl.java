package com.team.cklob.gami.domain.comment.service.impl;

import com.team.cklob.gami.domain.comment.dto.request.CommentCreateRequest;
import com.team.cklob.gami.domain.comment.dto.response.CommentResponse;
import com.team.cklob.gami.domain.comment.entity.Comment;
import com.team.cklob.gami.domain.comment.exception.ForbiddenCommentAccessException;
import com.team.cklob.gami.domain.comment.exception.NotFoundCommentException;
import com.team.cklob.gami.domain.comment.repository.CommentRepository;
import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.comment.service.CommentCommandService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandServiceImpl implements CommentCommandService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberUtil memberUtil;

    @Override
    public CommentResponse createComment(Long postId, CommentCreateRequest request) {
        Member currentMember = memberUtil.getCurrentMember();

        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        Comment comment = Comment.builder()
                .author(currentMember)
                .post(post)
                .content(request.getComment())
                .build();

        Comment saved = commentRepository.save(comment);

        return CommentResponse.from(saved);
    }

    @Override
    public void deleteComment(Long commentId) {
        Member currentMember = memberUtil.getCurrentMember();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(NotFoundCommentException::new);

        if (!comment.getAuthor().getId().equals(currentMember.getId())) {
            throw new ForbiddenCommentAccessException();
        }

        commentRepository.delete(comment);
    }
}
