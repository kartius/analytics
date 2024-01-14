package com.example.analytics.service.telegram.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebhookStatusInfoModel {

    boolean ok;
    WebhookResultModel result;
}
