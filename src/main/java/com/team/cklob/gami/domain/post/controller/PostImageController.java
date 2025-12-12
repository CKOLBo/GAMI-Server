package com.team.cklob.gami.domain.post.controller;

import com.team.cklob.gami.domain.post.dto.response.PostImageUploadResponse;
import com.team.cklob.gami.domain.post.service.PostImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/images")
public class PostImageController {

    private final PostImageService postImageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostImageUploadResponse> uploadPostImage(
            @RequestPart("image") MultipartFile image
    ) {
        PostImageUploadResponse response = postImageService.uploadImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping
    public ResponseEntity<Void> deletePostImage(
            @RequestParam("imageUrl") String imageUrl
    ) {
        postImageService.deleteImage(imageUrl);
        return ResponseEntity.noContent().build();
    }
}