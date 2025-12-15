package com.team.cklob.gami.domain.postlike.service;

public interface PostLikeService {
    void like(Long postId);
    void unlike(Long postId);
}
