package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.post.dto.request.PostImageRequest;
import com.team.cklob.gami.domain.post.dto.request.PostUpdateRequest;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.entity.PostImage;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.domain.post.service.PostUpdateService;
import com.team.cklob.gami.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PostUpdateServiceImpl implements PostUpdateService {

    private final PostRepository postRepository;
    private final S3Uploader s3Uploader;

    @Override
    public void update(Long postId, PostUpdateRequest request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(NotFoundPostException::new);

        if (request.getTitle() != null || request.getContent() != null) {
            post.update(
                    request.getTitle() != null ? request.getTitle() : post.getTitle(),
                    request.getContent() != null ? request.getContent() : post.getContent()
            );
        }

        if (request.getImages() == null) {
            return;
        }

        Map<String, PostImage> existingMap = post.getImages().stream()
                .collect(Collectors.toMap(PostImage::getImageUrl, Function.identity()));

        Set<String> requestUrls = request.getImages().stream()
                .map(PostImageRequest::getImageUrl)
                .collect(Collectors.toSet());

        List<PostImage> toRemove = post.getImages().stream()
                .filter(image -> !requestUrls.contains(image.getImageUrl()))
                .toList();

        for (PostImage image : toRemove) {
            s3Uploader.delete(image.getImageUrl());
            post.getImages().remove(image);
        }

        for (PostImageRequest imgReq : request.getImages()) {
            PostImage existing = existingMap.get(imgReq.getImageUrl());

            if (existing != null) {
                existing.updateSequence(imgReq.getSequence());
            } else {
                post.getImages().add(
                        PostImage.builder()
                                .post(post)
                                .imageUrl(imgReq.getImageUrl())
                                .sequence(imgReq.getSequence())
                                .build()
                );
            }
        }
    }
}
