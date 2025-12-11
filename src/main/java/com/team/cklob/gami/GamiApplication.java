package com.team.cklob.gami;

import com.team.cklob.gami.global.discord.DiscordProperties;
import com.team.cklob.gami.global.discord.DiscordWebhookService;
import com.team.cklob.gami.global.security.jwt.JwtProperties;
import com.team.cklob.gami.global.util.EnvironmentUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.LocalDateTime;

@ConfigurationPropertiesScan
@EnableJpaRepositories
@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({JwtProperties.class, DiscordProperties.class})
public class GamiApplication {


	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(GamiApplication.class, args);

		DiscordWebhookService discord = context.getBean(DiscordWebhookService.class);
		EnvironmentUtil env = context.getBean(EnvironmentUtil.class);

		discord.sendEmbedMessage(
				"🚀 서버 시작됨",
				"GAMI 서버 애플리케이션이 정상적으로 시작되었습니다.",
				LocalDateTime.now(),
				env.getEnvironment()
		);
	}
}
