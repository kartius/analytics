package com.example.analytics.service.telegram.util;

import com.example.analytics.config.telegram.TelegramBotConfig;
import com.example.analytics.service.telegram.model.WebhookResultModel;
import com.example.analytics.service.telegram.model.WebhookStatusInfoModel;
import com.example.analytics.service.telegram.service.WebhookStatusInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Component
public class WebhookStatusInfoChecker {

    WebhookStatusInfo webhookStatusInfo;
    TelegramBotConfig config;

    public boolean isWebhookActivated() {
        try {
            final WebhookStatusInfoModel model = webhookStatusInfo.getStatusInfo();
            final WebhookResultModel result = model.getResult();

            final boolean status = model.isOk()
                    && result.getUrl().equals(config.getWebhookPath())
                    && !result.getIp_address().isEmpty();

            if (model.isOk()) {
                log.info("Received webhook mode status from " + config.getTelegramApiUrl() +
                        " (" + ((status) ? "enabled" : "disabled") + ")");
            }

            return status;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
