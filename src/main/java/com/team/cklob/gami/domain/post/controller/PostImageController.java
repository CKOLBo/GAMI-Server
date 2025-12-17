package com.team.cklob.gami.domain.post.controller;

import com.team.cklob.gami.domain.post.dto.response.PostImageUploadResponse;
import com.team.cklob.gami.domain.post.service.PostImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post/images")
@Tag(name = "Post Image", description = "게시글 이미지 업로드 API")
@SecurityRequirement(name = "Bearer")
public class PostImageController {

    private final PostImageService postImageService;

    @Operation(
            summary = "게시글 이미지 업로드",
            description = "게시글에 사용할 이미지를 업로드한다 (Multipart)"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "이미지 업로드 성공",
                    content = @Content(schema = @Schema(implementation = PostImageUploadResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "400", description = "잘못된 파일 요청", content = @Content)
    })
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostImageUploadResponse> uploadPostImage(
            @Parameter(
                    description = "업로드할 이미지 파일",
                    required = true,
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            )
            @RequestPart("image") MultipartFile image
    ) {
        PostImageUploadResponse response = postImageService.uploadImage(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "게시글 이미지 삭제",
            description = "업로드된 게시글 이미지를 삭제한다"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "이미지 삭제 성공"),
            @ApiResponse(responseCode = "401", description = "인증 필요", content = @Content),
            @ApiResponse(responseCode = "404", description = "이미지를 찾을 수 없음", content = @Content)
    })
    @DeleteMapping
    public ResponseEntity<Void> deletePostImage(
            @Parameter(
                    description = "삭제할 이미지 URL",
                    example = "https://bucket.s3.ap-northeast-2.amazonaws.com/image.png"
            )
            @RequestParam("imageUrl") String imageUrl
    ) {
        postImageService.deleteImage(imageUrl);
        return ResponseEntity.noContent().build();
    }
}
