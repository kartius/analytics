package com.example.analytics.controller;

import com.example.analytics.config.telegram.TelegramBotConfig;
import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.bot.impl.TelegramBotWebhook;
import com.example.analytics.service.telegram.bot.instance.BotInstance;
import com.example.analytics.service.telegram.enums.BotMainModeTypes;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBotController {

    TelegramBotWebhook webhookBot;

    public TelegramBotController(Bot bot, @NotNull TelegramBotConfig config, BotInstance botInstance) {
        if (config.getBotMainMode().equals(BotMainModeTypes.POLLING.getValue())) {
            this.webhookBot = (TelegramBotWebhook) botInstance.getInstance(BotMainModeTypes.WEBHOOK.getValue());
        } else {
            this.webhookBot = (TelegramBotWebhook) bot;
        }
    }

    @PostMapping("/telegram")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return webhookBot.onWebhookUpdateReceived(update);
    }
}
