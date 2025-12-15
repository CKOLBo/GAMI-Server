package com.team.cklob.gami.domain.postlike.service.impl;

import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.postlike.entity.PostLike;
import com.team.cklob.gami.domain.postlike.exception.AlreadyLikedPostException;
import com.team.cklob.gami.domain.postlike.exception.InvalidMemberPrincipalException;
import com.team.cklob.gami.domain.postlike.exception.NotLikedPostException;
import com.team.cklob.gami.domain.postlike.repository.PostLikeRepository;
import com.team.cklob.gami.domain.postlike.service.PostLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Override
    public void like(Long postId) {
        log.info("[PostLike] like start postId={}", postId);

        String email = getEmail();
        log.info("[PostLike] email={}", email);

        if (postLikeRepository.existsByMemberEmailAndPost_Id(email, postId)) {
            throw new AlreadyLikedPostException();
        }

        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        postLikeRepository.save(
                PostLike.builder()
                        .memberEmail(email)
                        .post(post)
                        .build()
        );

        postRepository.increaseLike(postId);

        log.info("[PostLike] like success");
    }

    @Override
    public void unlike(Long postId) {
        log.info("[PostLike] unlike start postId={}", postId);

        String email = getEmail();

        PostLike postLike = postLikeRepository
                .findByMemberEmailAndPost_Id(email, postId)
                .orElseThrow(NotLikedPostException::new);

        postLikeRepository.delete(postLike);
        postRepository.decreaseLike(postId);

        log.info("[PostLike] unlike success");
    }

    private String getEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new InvalidMemberPrincipalException();
        }

        return authentication.getName();
    }
}
