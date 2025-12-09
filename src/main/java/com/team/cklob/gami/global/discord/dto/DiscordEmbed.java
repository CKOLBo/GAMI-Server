package com.team.cklob.gami.global.discord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class DiscordEmbed {
    private String content;
    private List<Map<String, Object>> embeds;
}
