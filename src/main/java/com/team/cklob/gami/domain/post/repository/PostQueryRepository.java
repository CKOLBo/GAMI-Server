package com.team.cklob.gami.domain.post.repository;

import com.team.cklob.gami.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final PostRepository postRepository;

    public Page<Post> search(String keyword, Pageable pageable) {
        if (StringUtils.hasText(keyword)) {
            return postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        }
        return postRepository.findAll(pageable);
    }
}
