package com.example.analytics.service.telegram.bot.impl;

import com.example.analytics.service.telegram.bot.Bot;
import com.example.analytics.service.telegram.handler.CommandHandler;
import com.example.analytics.service.telegram.util.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.starter.SpringWebhookBot;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Getter
public class TelegramBotWebhook extends SpringWebhookBot implements Bot, CommandHandler {

    String botPath;
    String botUsername;
    String botToken;
    Long targetChatId;
    String startupMessage;
    boolean isDeafModeEnabled;
    boolean isEchoModeEnabled;

    public TelegramBotWebhook(@NotNull Builder builder) {
        super(builder.setWebhook);
        this.botPath = builder.botPath;
        this.botUsername = builder.botUsername;
        this.botToken = builder.botToken;
        this.targetChatId = builder.targetChatId;
        this.startupMessage = builder.startupMessage;
        this.isDeafModeEnabled = builder.isDeafModeEnabled;
        this.isEchoModeEnabled = builder.isEchoModeEnabled;

        try {
            this.execute(new SetMyCommands(LIST_OF_COMMANDS, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(@NotNull Update update) {
        Long chatId;
        Long userId;
        String userName;
        String messageText;

        if (update.hasMessage() && update.getMessage().hasText()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();
            messageText = update.getMessage().getText();
            botAnswerUtils(messageText, chatId, userName);
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            messageText = update.getCallbackQuery().getData();
            botAnswerUtils(messageText, chatId, userName);
        }
        return null;
    }

    private void botAnswerUtils(@NotNull String messageText, Long chatId, String userName) {
        if (!isDeafModeEnabled()) {
            switch (messageText) {
                case "/analytic":
                    sendActualAnalyticData(chatId, userName);
                    break;
                case "/start":
                    sendStartMessage(chatId, userName);
                    break;
                case "/help":
                    sendHelpMessage(chatId, userName);
                    break;
                default:
                    if (isEchoModeEnabled()) {
                        sendMessage(Message.of(chatId, messageText));
                    }
                    //log.info("Unexpected message");
            }
        }
    }

    private void sendActualAnalyticData(Long chatId, String userName) {
        // ***************************************************************************************
        // here we call a method that will start collecting fresh analytics data from another bean
        // the analytics collection method must return a string
        // ***************************************************************************************
        final String actualAnalyticData = "New analytic data here!";

        final String text = "*" + userName + "*, here you are.\n\n" + actualAnalyticData;
        sendMessage(Message.of(chatId, text));
    }

    private void sendStartMessage(Long chatId, String userName) {
        sendMessage(Message.of(chatId, "Hello, *" + userName + "*!\n" +
                "I'm a *" + getBotUsername() + "*."));
    }

    private void sendHelpMessage(Long chatId, String userName) {
        sendMessage(Message.of(chatId, "Hello, *" + userName + "*!\n" + HELP_TEXT));
    }

    @Override
    public void sendMessage(SendMessage message) {
        try {
            this.execute(message);
            final String logText = "Telegram bot sent message";
            log.info(logText);
            log.debug(logText.concat(": ").concat(message.getText()));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
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
        SetWebhook setWebhook;
        String botPath;
        String botUsername;
        String botToken;
        Long targetChatId;
        String startupMessage;
        boolean isDeafModeEnabled;
        boolean isEchoModeEnabled;

        public Builder withSetWebhook(SetWebhook setWebhook) {
            this.setWebhook = setWebhook;
            return this;
        }

        public Builder withBotPath(String botPath) {
            this.botPath = botPath;
            return this;
        }

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

        public TelegramBotWebhook build() {
            return new TelegramBotWebhook(this);
        }
    }
}
