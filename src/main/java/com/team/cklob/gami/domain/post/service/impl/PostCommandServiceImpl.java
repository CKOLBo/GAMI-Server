package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.member.repository.MemberRepository;
import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandServiceImpl implements PostCommandService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Override
    public Long createPost(PostCreateRequest request) {
        Member member = memberRepository.findById(1L)
                .orElseThrow(() -> new IllegalStateException("테스트용 멤버가 없습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

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
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        postRepository.delete(post);
    }
}