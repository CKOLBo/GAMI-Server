package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostUpdateServiceImpl implements PostUpdateService {

    private final PostRepository postRepository;

    @Override
    public void update(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        Post updatedPost = Post.builder()
                .id(post.getId())
                .member(post.getMember())
                .title(request.getTitle())
                .content(request.getContent())
                .likeCount(post.getLikeCount())
                .build();

        postRepository.save(updatedPost);
    }
}
