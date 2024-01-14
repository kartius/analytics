package com.example.analytics.service.telegram.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public enum BotMainModeTypes {

    POLLING("polling"),
    WEBHOOK("webhook");

    String value;
}
