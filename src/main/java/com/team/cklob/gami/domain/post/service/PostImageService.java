package com.team.cklob.gami.domain.post.service;

import com.team.cklob.gami.domain.post.dto.response.PostImageUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PostImageService {

    PostImageUploadResponse uploadImage(MultipartFile image);

    void deleteImage(String imageUrl);
}
