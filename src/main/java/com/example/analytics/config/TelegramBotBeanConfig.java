package com.example.analytics.config;

import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.bot.instance.BotInstance;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

@Slf4j
@Configuration
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBotBeanConfig {

    TelegramBotConfig config;

    @Bean
    public SetWebhook setWebhookInstance() {
        return SetWebhook.builder()
                .url(config.getWebhookPath())
                .build();
    }

    @Bean
    public Bot bot(@NotNull BotInstance botInstance) {
        return botInstance.getInstance(config.getBotMainMode());
    }
}
