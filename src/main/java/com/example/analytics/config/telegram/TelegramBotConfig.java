package com.example.analytics.config.telegram;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:telegram.properties")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
public class TelegramBotConfig {

    @Value("${telegram.api-url}")
    String telegramApiUrl;

    @Value("${bot.token}")
    String telegramBotToken;

    @Value("${bot.name}")
    String telegramBotName;

    @Value("${bot.target-chatId}")
    Long targetChatId;

    @Value("${bot.main-mode}")
    String botMainMode;

    @Value("${bot.webhook-path}")
    String webhookPath;

    @Value("${bot.startup-message}")
    String startupMessage;

    @Value("${bot.deaf-mode}")
    boolean isDeafModeEnabled;

    @Value("${bot.echo-mode}")
    boolean isEchoModeEnabled;
}
