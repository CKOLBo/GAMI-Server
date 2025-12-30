package com.team.cklob.gami.domain.admin.service.impl;

import com.team.cklob.gami.domain.admin.service.AdminPostService;
import com.team.cklob.gami.domain.comment.repository.CommentRepository;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostImageRepository;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.postlike.repository.PostLikeRepository;
import com.team.cklob.gami.domain.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminPostServiceImpl implements AdminPostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final PostImageRepository postImageRepository;
    private final PostLikeRepository postLikeRepository;
    private final ReportRepository reportRepository;

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        commentRepository.deleteAllByPostId(postId);
        postRepository.deleteAllFromLikeTableByPostId(postId);
        postImageRepository.deleteAllByPostId(postId);
        postLikeRepository.deleteAllByPostId(postId);
        reportRepository.deleteAllByPostId(postId);

        postRepository.delete(post);
    }
}
