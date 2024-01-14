package com.example.analytics.service.telegram.variables;

import com.example.analytics.config.TelegramBotConfig;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class TelegramApiRequestUrl {

    String webhookTurnOnRequestUrl;
    String webhookTurnOffRequestUrl;
    String webhookStatusInfoRequestUrl;

    public TelegramApiRequestUrl(@NotNull TelegramBotConfig config) {
        this.webhookTurnOnRequestUrl = config.getTelegramApiUrl()
                .concat("bot")
                .concat(config.getTelegramBotToken())
                .concat("/setWebhook?url=")
                .concat(config.getWebhookPath());
        this.webhookTurnOffRequestUrl = config.getTelegramApiUrl()
                .concat("bot")
                .concat(config.getTelegramBotToken())
                .concat("/setWebhook?remove");
        this.webhookStatusInfoRequestUrl = config.getTelegramApiUrl()
                .concat("bot")
                .concat(config.getTelegramBotToken())
                .concat("/getWebhookInfo");
    }
}
