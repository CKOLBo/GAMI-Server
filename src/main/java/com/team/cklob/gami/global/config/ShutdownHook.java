package com.team.cklob.gami.global.config;

import com.team.cklob.gami.global.discord.DiscordWebhookService;
import com.team.cklob.gami.global.util.EnvironmentUtil;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ShutdownHook {

    private final DiscordWebhookService discordWebhookService;
    private final EnvironmentUtil environmentUtil;

    @PreDestroy
    public void onShutdown() {
        discordWebhookService.sendEmbedMessage(
                "🛑 서버 종료됨",
                "GAMI 서버 애플리케이션이 종료되었습니다.",
                LocalDateTime.now(),
                environmentUtil.getEnvironment()
        );
    }
}
