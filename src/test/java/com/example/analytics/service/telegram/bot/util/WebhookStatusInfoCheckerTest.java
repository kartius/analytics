package com.example.analytics.service.telegram.bot.util;

import com.example.analytics.config.telegram.TelegramBotConfig;
import com.example.analytics.service.telegram.model.WebhookResultModel;
import com.example.analytics.service.telegram.model.WebhookStatusInfoModel;
import com.example.analytics.service.telegram.service.WebhookStatusInfo;
import com.example.analytics.service.telegram.util.WebhookStatusInfoChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WebhookStatusInfoCheckerTest {

    @Mock
    private WebhookStatusInfo webhookStatusInfo;

    @Mock
    private TelegramBotConfig config;

    @InjectMocks
    private WebhookStatusInfoChecker webhookStatusInfoChecker;

    private WebhookStatusInfoModel model;

    @BeforeEach
    public void setup() {
        model = new WebhookStatusInfoModel();
        model.setOk(true);
    }

    @Test
    @DisplayName("If webhook is activated.")
    void ifWebhookIsActivatedTest() throws IOException {
        WebhookResultModel result = new WebhookResultModel();
        result.setUrl("https://126.12.33.245:443/telegram");
        result.setIp_address("127.0.0.1");
        model.setResult(result);

        when(webhookStatusInfo.getStatusInfo()).thenReturn(model);
        when(config.getWebhookPath()).thenReturn("https://126.12.33.245:443/telegram");
        when(config.getTelegramApiUrl()).thenReturn("https://api.telegram.org/");

        boolean resultStatus = webhookStatusInfoChecker.isWebhookActivated();
        assertThat(resultStatus).isTrue();
    }

    @Test
    @DisplayName("Get exception in result.")
    void getExceptionInResultTest() throws IOException {
        when(webhookStatusInfo.getStatusInfo()).thenThrow(new IOException());
        assertThatThrownBy(() -> webhookStatusInfoChecker.isWebhookActivated())
                .isInstanceOf(RuntimeException.class);

        verify(webhookStatusInfo, times(1)).getStatusInfo();
    }
}
