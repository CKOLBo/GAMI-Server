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
import software.amazon.awssdk.services.s3.model.S3Exception;

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

        String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new PostInvalidImageExtensionException();
        }

        String key = buildKey(dirName, extension);

        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(file.getBytes())
            );

        } catch (IOException | S3Exception e) {
            throw new PostImageUploadFailedException();
        }

        return urlPrefix.endsWith("/") ? urlPrefix + key : urlPrefix + "/" + key;
    }

    public void delete(String imageUrl) {
        if (imageUrl == null || imageUrl.isBlank()) {
            return;
        }

        String key = extractKey(imageUrl);
        if (key == null || key.isBlank()) {
            return;
        }

        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .build();

            s3Client.deleteObject(deleteObjectRequest);

        } catch (S3Exception e) {
            throw new PostImageDeleteFailedException();
        }
    }

    private String buildKey(String dirName, String extension) {
        String uuid = UUID.randomUUID().toString();
        return (dirName == null || dirName.isBlank())
                ? uuid + "." + extension
                : dirName + "/" + uuid + "." + extension;
    }

    private String extractKey(String imageUrl) {
        String normalizedPrefix = urlPrefix.endsWith("/")
                ? urlPrefix.substring(0, urlPrefix.length() - 1)
                : urlPrefix;

        String normalizedUrl = imageUrl.startsWith(normalizedPrefix)
                ? imageUrl.substring(normalizedPrefix.length())
                : imageUrl;

        if (normalizedUrl.startsWith("/")) {
            normalizedUrl = normalizedUrl.substring(1);
        }

        return normalizedUrl;
    }
}
