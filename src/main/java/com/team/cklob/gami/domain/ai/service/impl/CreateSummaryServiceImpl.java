package com.team.cklob.gami.domain.ai.service.impl;

import com.team.cklob.gami.domain.ai.dto.request.GeminiRequest;
import com.team.cklob.gami.domain.ai.dto.response.GeminiResponse;
import com.team.cklob.gami.domain.ai.service.CreateSummaryService;
import com.team.cklob.gami.domain.post.entity.Post;
import com.team.cklob.gami.domain.post.exception.NotFoundPostException;
import com.team.cklob.gami.domain.post.repository.PostRepository;
import com.team.cklob.gami.global.event.CreateSummaryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateSummaryServiceImpl implements CreateSummaryService {

    private final PostRepository postRepository;
    private final WebClient webClient;

    @Value("${gemini.api-key}")
    private String apiKey;

    @Value("${gemini.model}")
    private String model;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void execute(CreateSummaryEvent event) {
        try {
            Post post = postRepository.findById(event.postId())
                    .orElseThrow(NotFoundPostException::new);

            String prompt = """
                    너는 게시글 요약 전문가야.
                    아래의 글을 3-5문장으로 한국어로 요약해줘.
                    핵심 내용만 간결하게 정리하고, 원문의 어조를 유지해줘.
                    
                    [게시글 내용]
                    %s
                    """.formatted(event.content());

            String summary = callGeminiApi(prompt);
            post.updateSummary(summary);
        } catch (Exception e) {
            log.error("요약 생성 실패 - Post ID: {}, Error: {}", event.postId(), e.getMessage(), e);
        }
    }

    private String callGeminiApi(String prompt) {
        try {
            GeminiRequest.Part part = new GeminiRequest.Part();
            part.setText(prompt);

            GeminiRequest.Content content = new GeminiRequest.Content();
            content.setParts(List.of(part));

            GeminiRequest request = new GeminiRequest();
            request.setContents(List.of(content));

            GeminiResponse response = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/models/{model}:generateContent")
                            .queryParam("key", apiKey)
                            .build(model))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(GeminiResponse.class)
                    .block();


            if (response != null &&
                    response.getCandidates() != null &&
                    !response.getCandidates().isEmpty()) {

                String summary = response.getCandidates().get(0)
                        .getContent()
                        .getParts().get(0)
                        .getText();

                log.debug("Gemini API 응답 성공");
                return summary;
            }

            log.warn("Gemini API 응답이 비어있습니다.");
            return "요약을 생성할 수 없습니다.";

        } catch (Exception e) {
            log.error("Gemini API 호출 중 오류 발생: {}", e.getMessage(), e);
            throw new RuntimeException("Gemini API 호출 실패", e);
        }
    }
}