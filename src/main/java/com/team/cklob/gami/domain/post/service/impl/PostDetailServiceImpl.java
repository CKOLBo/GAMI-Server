package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.post.dto.response.PostResponse;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostDetailService;
import com.team.cklob.gami.domain.postlike.repository.PostLikeRepository;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostDetailServiceImpl implements PostDetailService {

    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final MemberUtil memberUtil;

    @Override
    public PostResponse get(Long postId) {
        Post post = postRepository.findPostDetail(postId)
                .orElseThrow(NotFoundPostException::new);

        int commentCount = postRepository.countComments(postId);

        boolean isLiked = false;

        try {
            String memberEmail = memberUtil.getCurrentMember().getEmail();
            isLiked = postLikeRepository.existsByMemberEmailAndPost_Id(
                    memberEmail,
                    postId
            );
        } catch (Exception ignored) {
        }

        return PostResponse.from(
                post,
                commentCount,
                isLiked
        );
    }
}
