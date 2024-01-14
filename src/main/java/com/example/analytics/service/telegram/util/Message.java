package com.example.analytics.service.telegram.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Slf4j
public class Message {

    public static @NotNull SendMessage of(Long chatId, String text) {
        final SendMessage message = new SendMessage();
        message.enableMarkdown(true);
        message.setChatId(chatId);
        message.setText(text);
        // inline keyboard for every message replay
        //message.setReplyMarkup(ButtonHandler.inlineMarkup());
        return message;
    }
}
