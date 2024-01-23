package com.example.analytics.service.telegram.bot.instance;

import com.example.analytics.config.telegram.TelegramBotConfig;
import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.bot.impl.TelegramBotPolling;
import com.example.analytics.service.telegram.bot.impl.TelegramBotWebhook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BotInstanceTest {
    private BotInstance botInstance;
    private Bot bot;

    @BeforeEach
    public void setup() {
        botInstance = new BotInstance(new TelegramBotConfig(), new SetWebhook());
    }

    @Test
    @DisplayName("Get bot polling instance.")
    void getPollingInstanceTest() {
        bot = botInstance.getInstance("polling");
        assertThat(bot).isNotNull();
        assertThat(bot).isInstanceOf(TelegramBotPolling.class);
    }

    @Test
    @DisplayName("Get webhook instance.")
    void getWebhookInstanceTest() {
        bot = botInstance.getInstance("webhook");
        assertThat(bot).isNotNull();
        assertThat(bot).isInstanceOf(TelegramBotWebhook.class);
    }

    @Test
    @DisplayName("Get unknown instance & throw exception")
    void getInvalidInstanceTest() {
        String errorString = "An error occurred when trying to determine the bot operating mode (polling or webhook)";
        Executable executable = () -> botInstance.getInstance("hillel");
        RuntimeException exception = assertThrows(RuntimeException.class, executable);
        assertThat(exception.getMessage()).isEqualTo(errorString);
    }
}
