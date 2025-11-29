package com.team.cklob.gami.domain.post.repository;

import com.team.cklob.gami.domain.post.dto.request.PostSearchRequest;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostPageException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class PostQueryRepository {

    private final PostRepository postRepository;

    public Page<Post> search(PostSearchRequest request) {
        int page = request.getPage() != null ? request.getPage() : 0;
        int size = request.getSize() != null ? request.getSize() : 10;

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Post> result;
        if (StringUtils.hasText(request.getTitle())) {
            result = postRepository.findByTitleContainingIgnoreCase(request.getTitle(), pageable);
        } else {
            result = postRepository.findAll(pageable);
        }

        if (page > 0 && result.getContent().isEmpty()) {
            throw new NotFoundPostPageException();
        }

        return result;
    }
}
