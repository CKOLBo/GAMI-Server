package com.team.cklob.gami.domain.post.dto.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSearchRequest {

    private String title;

    @Min(0)
    private Integer page = 0;

    @Min(1)
    private Integer size = 10;

    private String sort = "createdAt,desc";
}
