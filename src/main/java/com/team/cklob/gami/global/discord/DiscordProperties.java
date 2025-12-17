package com.team.cklob.gami.global.discord;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "discord")
public class DiscordProperties {
    private final String webhookUrl;

    public DiscordProperties(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
}
