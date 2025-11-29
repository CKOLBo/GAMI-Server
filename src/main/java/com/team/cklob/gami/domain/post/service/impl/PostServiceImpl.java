package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.member.entity.Member;
import com.team.cklob.gami.domain.post.dto.request.PostCreateRequest;
import com.team.cklob.gami.domain.post.dto.request.PostSearchRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.dto.response.PostListResponse;
import com.team.cklob.gami.domain.post.dto.response.PostResponse;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.ForbiddenPostAccessException;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostQueryRepository;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostService;
import com.team.cklob.gami.global.util.MemberUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostQueryRepository postQueryRepository;
    private final MemberUtil memberUtil;

    @Override
    @Transactional
    public PostResponse createPost(PostCreateRequest request) {
        Member member = memberUtil.getCurrentMember();
        Post post = Post.create(member, request.getTitle(), request.getContent());
        Post saved = postRepository.save(post);
        return PostResponse.from(saved);
    }

    @Override
    public List<PostListResponse> getPostList(PostSearchRequest request) {
        Page<Post> page = postQueryRepository.search(request);
        return page.getContent().stream()
                .map(PostListResponse::from)
                .toList();
    }

    @Override
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
        return PostResponse.from(post);
    }

    @Override
    @Transactional
    public PostResponse updatePost(Long id, PostUpdateRequest request) {
        Member currentMember = memberUtil.getCurrentMember();
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);

        if (!post.getMember().getId().equals(currentMember.getId())) {
            throw new ForbiddenPostAccessException();
        }

        post.update(request.getTitle(), request.getContent());
        return PostResponse.from(post);
    }

    @Override
    @Transactional
    public void deletePost(Long id) {
        Member currentMember = memberUtil.getCurrentMember();
        Post post = postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);

        if (!post.getMember().getId().equals(currentMember.getId())) {
            throw new ForbiddenPostAccessException();
        }

        postRepository.delete(post);
    }
}
