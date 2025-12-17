package com.team.cklob.gami.domain.post.service.impl;

import com.team.cklob.gami.domain.post.dto.response.PostImageUploadResponse;
import com.team.cklob.gami.domain.post.service.PostImageService;
import com.team.cklob.gami.global.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PostImageServiceImpl implements PostImageService {

    private final S3Uploader s3Uploader;

    @Override
    public PostImageUploadResponse uploadImage(MultipartFile image) {
        String imageUrl = s3Uploader.upload(image, "post");
        return new PostImageUploadResponse(imageUrl);
    }

    @Override
    public void deleteImage(String imageUrl) {
        s3Uploader.delete(imageUrl);
    }
}
