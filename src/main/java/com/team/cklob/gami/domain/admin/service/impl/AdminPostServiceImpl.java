package com.team.cklob.gami.domain.admin.service.impl;

import com.team.cklob.gami.domain.admin.service.AdminPostService;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminPostServiceImpl implements AdminPostService {

    private final PostRepository postRepository;

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        postRepository.delete(post);
    }
}
