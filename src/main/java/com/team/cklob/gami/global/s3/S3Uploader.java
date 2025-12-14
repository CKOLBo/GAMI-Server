package com.team.cklob.gami.global.s3;

import com.team.cklob.gami.domain.post.exception.PostImageDeleteFailedException;
import com.team.cklob.gami.domain.post.exception.PostImageUploadFailedException;
import com.team.cklob.gami.domain.post.exception.PostInvalidImageExtensionException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class S3Uploader {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url-prefix}")
    private String urlPrefix;

    private static final List<String> ALLOWED_EXTENSIONS =
            List.of("jpg", "jpeg", "png", "gif", "webp");

    public String upload(MultipartFile file, String dirName) {
        if (file == null || file.isEmpty()) {
            throw new PostInvalidImageExtensionException();
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new PostInvalidImageExtensionException();
        }

        String extension =
                originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new PostInvalidImageExtensionException();
        }

        String key = dirName + "/" + UUID.randomUUID() + "." + extension;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

            return urlPrefix + "/" + key;
        } catch (IOException e) {
            throw new PostImageUploadFailedException();
        }
    }

    public void delete(String imageUrl) {
        try {
            String key = imageUrl.substring(urlPrefix.length() + 1);

            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build());
        } catch (Exception e) {
            throw new PostImageDeleteFailedException();
        }
    }
}
