package com.example.analytics.service.telegram.service;

import com.example.analytics.service.telegram.model.WebhookSwitchModel;
import com.example.analytics.service.telegram.util.JSONModelObjectParser;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class WebhookSwitch {

    public WebhookSwitchModel toggleSwitch(@NotNull String urlRequest) throws IOException {
        log.debug("Trying to switch webhook mode and create result model");

        final WebhookSwitchModel model = new WebhookSwitchModel();

        final JSONObject object = JSONModelObjectParser.getObject(urlRequest);

        model.setOk(object.getBoolean("ok"));
        model.setResult(object.optBoolean("result", false));
        model.setError_code(object.optInt("error_code", -1));
        model.setDescription(object.getString("description"));

        return model;
    }
}
