package com.example.analytics.service.telegram.service;

import com.example.analytics.service.telegram.model.WebhookResultModel;
import com.example.analytics.service.telegram.model.WebhookStatusInfoModel;
import com.example.analytics.service.telegram.util.JSONModelObjectParser;
import com.example.analytics.service.telegram.variables.TelegramApiRequestUrl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class WebhookStatusInfo {

    TelegramApiRequestUrl telegramApiRequestUrl;

    public WebhookStatusInfo(TelegramApiRequestUrl telegramApiRequestUrl) {
        this.telegramApiRequestUrl = telegramApiRequestUrl;
    }

    public WebhookStatusInfoModel getStatusInfo() throws IOException {
        log.debug("Trying to check webhook status from the internet and create result model");

        final WebhookStatusInfoModel statusInfoModel = new WebhookStatusInfoModel();
        final WebhookResultModel resultModel = new WebhookResultModel();

        final JSONObject statusInfoJson = JSONModelObjectParser.getObject(telegramApiRequestUrl.getWebhookStatusInfoRequestUrl());
        final JSONObject resultJson = statusInfoJson.getJSONObject("result");

        statusInfoModel.setOk(statusInfoJson.getBoolean("ok"));

        resultModel.setUrl(resultJson.getString("url"));
        resultModel.setHas_custom_certificate(resultJson.getBoolean("has_custom_certificate"));
        resultModel.setPending_update_count(resultJson.getInt("pending_update_count"));

        resultModel.setMax_connections(resultJson.optInt("max_connections", -1));
        resultModel.setIp_address(resultJson.optString("ip_address", ""));

        statusInfoModel.setResult(resultModel);

        return statusInfoModel;
    }
}
