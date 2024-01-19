package com.example.analytics.service.telegram.bot.impl;

import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.handler.CommandHandler;
import com.example.analytics.service.telegram.handler.MessageHandler;
import com.example.analytics.service.telegram.util.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class TelegramBotPolling extends TelegramLongPollingBot implements Bot, CommandHandler {

    String botUsername;
    String botToken;
    Long targetChatId;
    String startupMessage;
    boolean isDeafModeEnabled;
    boolean isEchoModeEnabled;
    MessageHandler<TelegramBotPolling> messageHandler;

    public TelegramBotPolling(@NotNull Builder builder) {
        super(new DefaultBotOptions(), builder.botToken);

        this.botUsername = builder.botUsername;
        this.botToken = builder.botToken;
        this.targetChatId = builder.targetChatId;
        this.startupMessage = builder.startupMessage;
        this.isDeafModeEnabled = builder.isDeafModeEnabled;
        this.isEchoModeEnabled = builder.isEchoModeEnabled;

        this.messageHandler = new MessageHandler<>(this);

        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public void onUpdateReceived(@NotNull Update update) {
        messageHandler.onUpdateReceived(update);
    }

    @Override
    public void sendMessage(SendMessage message) {
        messageHandler.send(message);
    }

    @Override
    public void onRegister() {
        if (!startupMessage.isEmpty()) {
            sendMessage(Message.of(targetChatId, startupMessage));
        }
        log.info("Telegram bot service successfully registered");
    }

    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class Builder {
        String botUsername;
        String botToken;
        Long targetChatId;
        String startupMessage;
        boolean isDeafModeEnabled;
        boolean isEchoModeEnabled;

        public Builder withBotUsername(String botUsername) {
            this.botUsername = botUsername;
            return this;
        }

        public Builder withBotToken(String botToken) {
            this.botToken = botToken;
            return this;
        }

        public Builder withTargetChatId(Long targetChatId) {
            this.targetChatId = targetChatId;
            return this;
        }

        public Builder withStartupMessage(String startupMessage) {
            this.startupMessage = startupMessage;
            return this;
        }

        public Builder withIsDeafModeEnabled(boolean isDeafModeEnabled) {
            this.isDeafModeEnabled = isDeafModeEnabled;
            return this;
        }

        public Builder withIsEchoModeEnabled(boolean isEchoModeEnabled) {
            this.isEchoModeEnabled = isEchoModeEnabled;
            return this;
        }

        public TelegramBotPolling build() {
            return new TelegramBotPolling(this);
        }
    }
}
