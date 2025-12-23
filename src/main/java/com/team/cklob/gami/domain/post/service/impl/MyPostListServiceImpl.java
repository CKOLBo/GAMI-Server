package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.post.dto.response.PostListResponse;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.MyPostListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPostListServiceImpl implements MyPostListService {

    private final PostRepository postRepository;

    @Override
    public List<PostListResponse> getMyPosts(Long memberId) {
        return postRepository.findAllByMemberId(memberId)
                .stream()
                .map(PostListResponse::from)
                .toList();
    }
}
