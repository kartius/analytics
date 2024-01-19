package com.example.analytics.service.telegram;

import com.example.analytics.config.telegram.TelegramBotConfig;
import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.util.Message;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TelegramBotService {

    Bot bot;
    TelegramBotConfig config;

    @Autowired
    public TelegramBotService(Bot bot, @NotNull TelegramBotConfig config) {
        this.bot = bot;
        this.config = config;
        log.debug("Telegram bot service successfully initialized");
    }

    /**
     * This is the main method of publishing messages in a telegram using this bot.
     *
     * @param text - any string passed as this argument will be published in the telegram channel
     *             for which the bot’s rights to publish messages are configured.
     */
    public synchronized void broadcast(String text) {
        log.trace("Call broadcast() method with parameter: {}", text);
        bot.sendMessage(Message.of(config.getTargetChatId(), text));
    }
}
