package com.team.cklob.gami.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSearchRequest {

    private String keyword;
    private Integer page;
    private Integer size;
    private String sort;
}
