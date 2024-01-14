package com.example.analytics.service.telegram.bot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@FunctionalInterface
public interface Bot {

    void sendMessage(SendMessage message);
}
