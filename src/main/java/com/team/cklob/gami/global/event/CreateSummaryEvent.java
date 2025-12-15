package com.team.cklob.gami.global.event;

public record CreateSummaryEvent(
        Long postId,
        String content
) {
}
