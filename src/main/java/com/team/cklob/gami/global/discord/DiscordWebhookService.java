package com.team.cklob.gami.global.discord;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscordWebhookService {

    @Value("${discord.webhook-url}")
    private String webhookUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    private static final DateTimeFormatter TIME_FORMATTER =
            DateTimeFormatter.ofPattern("a hh시 mm분 ss초");

    public void sendEmbedMessage(String title, String description, LocalDateTime time, String environment) {

        String formattedDate = time.format(DATE_FORMATTER);
        String formattedTime = time.format(TIME_FORMATTER);

        // 환경에 따라 색상 다르게
        int color = switch (environment) {
            case "docker" -> 0x1ABC9C;      // 민트색
            case "cloudtype" -> 0x7289DA;   // 보라
            default -> 0x2ECC71;            // 초록색
        };

        Map<String, Object> embed = Map.of(
                "title", title,
                "description", description,
                "color", color,
                "fields", List.of(
                        Map.of("name", "환경", "value", environment, "inline", false),
                        Map.of("name", "날짜", "value", formattedDate, "inline", false),
                        Map.of("name", "시간", "value", formattedTime, "inline", false)
                )
        );

        Map<String, Object> body = Map.of("embeds", List.of(embed));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            restTemplate.postForEntity(webhookUrl, new HttpEntity<>(body, headers), String.class);
            log.info("Discord webhook message sent.");
        } catch (Exception e) {
            log.error("Failed to send webhook message: {}", e.getMessage());
        }
    }
}
