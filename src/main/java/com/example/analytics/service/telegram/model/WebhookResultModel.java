package com.example.analytics.service.telegram.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WebhookResultModel {

    String url;
    boolean has_custom_certificate;
    int pending_update_count;
    int max_connections;
    String ip_address;
}
