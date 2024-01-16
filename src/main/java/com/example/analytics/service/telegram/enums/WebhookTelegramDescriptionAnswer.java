package com.example.analytics.service.telegram.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public enum WebhookTelegramDescriptionAnswer {

    WEBHOOK_ENABLED_ANSWER("Webhook was set"),
    WEBHOOK_DISABLED_ANSWER("Webhook was deleted"),
    WEBHOOK_IS_ALREADY_DELETED("Webhook is already deleted"),
    WEBHOOK_BAD_REQUEST("Bad Request");

    String answer;
}
