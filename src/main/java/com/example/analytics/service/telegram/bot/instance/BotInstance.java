package com.example.analytics.service.telegram.bot.instance;

import com.example.analytics.config.TelegramBotConfig;
import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.bot.impl.TelegramBotPolling;
import com.example.analytics.service.telegram.bot.impl.TelegramBotWebhook;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Component
public class BotInstance {

    TelegramBotConfig config;
    SetWebhook webhook;

    public Bot getInstance(@NotNull String botMainMode) {
        switch (botMainMode) {
            case "polling" -> {
                return new TelegramBotPolling.Builder()
                        .withBotUsername(config.getTelegramBotName())
                        .withBotToken(config.getTelegramBotToken())
                        .withTargetChatId(config.getTargetChatId())
                        .withIsDeafModeEnabled(config.isDeafModeEnabled())
                        .withIsEchoModeEnabled(config.isEchoModeEnabled())
                        .withStartupMessage(config.getStartupMessage())
                        .build();
            }
            case "webhook" -> {
                return new TelegramBotWebhook.Builder()
                        .withSetWebhook(webhook)
                        .withBotPath(config.getWebhookPath())
                        .withBotUsername(config.getTelegramBotName())
                        .withBotToken(config.getTelegramBotToken())
                        .withTargetChatId(config.getTargetChatId())
                        .withIsDeafModeEnabled(config.isDeafModeEnabled())
                        .withIsEchoModeEnabled(config.isEchoModeEnabled())
                        .withStartupMessage(config.getStartupMessage())
                        .build();
            }
            default -> {
                final String botModeSelectErrorMessage = "An error occurred when trying to determine the bot operating mode (polling or webhook)";
                log.error(botModeSelectErrorMessage);
                throw new RuntimeException(botModeSelectErrorMessage);
            }
        }
    }
}
