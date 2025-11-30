package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostCommandService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;
    private final MemberUtil memberUtil;

    @Override
    public Long createPost(PostCreateRequest request) {
        Member member = memberUtil.getCurrentMember();

        Post post = Post.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(0)
                .createdAt(LocalDateTime.now())
                .updateAt(LocalDateTime.now())
                .build();

        return postRepository.save(post).getId();
    }

    @Override
    public void updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        Post updatedPost = Post.builder()
                .id(post.getId())
                .member(post.getMember())
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(post.getLikeCount())
                .createdAt(post.getCreatedAt())
                .updateAt(LocalDateTime.now())
                .build();

        postRepository.save(updatedPost);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        postRepository.delete(post);
    }
}
