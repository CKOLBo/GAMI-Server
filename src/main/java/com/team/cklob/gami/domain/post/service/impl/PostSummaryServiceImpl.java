package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.post.dto.response.PostSummaryResponse;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.MissingSummaryException;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostSummaryServiceImpl implements PostSummaryService {

    private final PostRepository postRepository;

    @Override
    @Transactional(readOnly = true)
    public PostSummaryResponse execute(Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        if (post.getSummary() == null) {
            throw new MissingSummaryException();
        }

        return new PostSummaryResponse(postId, post.getSummary());
    }
}
