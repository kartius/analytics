package com.example.analytics.service.telegram.initializer;

import com.example.analytics.config.telegram.TelegramBotConfig;
import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.bot.impl.TelegramBotPolling;
import com.example.analytics.service.telegram.bot.impl.TelegramBotWebhook;
import com.example.analytics.service.telegram.enums.WebhookTelegramDescriptionAnswer;
import com.example.analytics.service.telegram.model.WebhookSwitchModel;
import com.example.analytics.service.telegram.service.WebhookSwitch;
import com.example.analytics.service.telegram.util.WebhookStatusInfoChecker;
import com.example.analytics.service.telegram.variables.TelegramApiRequestUrl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.meta.generics.WebhookBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

@Slf4j
@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBotInitializer {

    Bot bot;
    TelegramBotConfig config;
    SetWebhook webhook;
    WebhookSwitch webhookSwitch;
    TelegramApiRequestUrl telegramApiRequestUrl;
    @NonFinal
    boolean isWebhookActivated;

    @Autowired
    public TelegramBotInitializer(Bot bot,
                                  TelegramBotConfig config,
                                  SetWebhook webhook,
                                  WebhookSwitch webhookSwitch, TelegramApiRequestUrl telegramApiRequestUrl,
                                  @NotNull WebhookStatusInfoChecker webhookStatusInfoChecker) {
        this.bot = bot;
        this.config = config;
        this.webhook = webhook;
        this.webhookSwitch = webhookSwitch;
        this.telegramApiRequestUrl = telegramApiRequestUrl;
        this.isWebhookActivated = webhookStatusInfoChecker.isWebhookActivated();

        try {
            webhookModeStatusControl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() {
        try {
            log.info("Trying to register telegram bot service in {} mode", config.getBotMainMode());
            final TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            if (bot instanceof TelegramBotPolling) {
                api.registerBot((LongPollingBot) bot);
            } else if (bot instanceof TelegramBotWebhook) {
                api.registerBot((WebhookBot) bot, webhook);
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void webhookModeStatusControl() throws IOException {
        final String switchErrorMessage = "An unknown error occurred while trying to switch webhook mode.\n" +
                "Check your internet connection and try again.\n" +
                "If the error persists, check the bot.webhook-path settings, most likely the error is there.";

        WebhookSwitchModel model;
        switch (config.getBotMainMode()) {
            case ("polling") -> {
                if (isWebhookActivated) {
                    log.info("Trying to switch telegram bot in to polling mode...");
                    model = webhookSwitch.toggleSwitch(telegramApiRequestUrl.getWebhookTurnOffRequestUrl());
                    if (model.getDescription()
                            .equals(WebhookTelegramDescriptionAnswer.WEBHOOK_DISABLED_ANSWER.getAnswer())) {
                        this.isWebhookActivated = false;
                        log.info("Webhook mode successfully turned off");
                    } else {
                        log.error(switchErrorMessage);
                        throw new RuntimeException(switchErrorMessage);
                    }
                }
            }
            case ("webhook") -> {
                if (!isWebhookActivated) {
                    log.info("Trying to switch telegram bot in to webhook mode...");
                    model = webhookSwitch.toggleSwitch(telegramApiRequestUrl.getWebhookTurnOnRequestUrl());
                    if (model.getDescription()
                            .equals(WebhookTelegramDescriptionAnswer.WEBHOOK_ENABLED_ANSWER.getAnswer())) {
                        this.isWebhookActivated = true;
                        log.info("Webhook mode successfully turned on");
                    } else if (!model.isOk()
                            && model.getError_code() == 400
                            && model.getDescription().contains(WebhookTelegramDescriptionAnswer.WEBHOOK_BAD_REQUEST.getAnswer())) {
                        log.error(model.getDescription());
                        throw new RuntimeException(model.getDescription());
                    } else {
                        log.error(switchErrorMessage);
                        throw new RuntimeException(switchErrorMessage);
                    }
                }
            }
            default -> {
                final String botModeSelectErrorMessage = "An error occurred when trying to determine the bot operating mode (polling or webhook)";
                log.error(botModeSelectErrorMessage);
                throw new RuntimeException(botModeSelectErrorMessage);
            }
        }
        log.info("Webhook mode status checked successfully");
    }
}
