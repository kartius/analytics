package com.example.analytics.service.telegram.handler;

import com.example.analytics.config.TelegramBotConfig;
import com.example.analytics.service.telegram.util.Message;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import static com.example.analytics.service.telegram.handler.CommandHandler.HELP_TEXT;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MessageHandler<T extends DefaultAbsSender> {

    TelegramBotConfig config = new AnnotationConfigApplicationContext(TelegramBotConfig.class)
            .getBean(TelegramBotConfig.class);
    T bot;

    public MessageHandler(T bot) {
        this.bot = bot;
    }

    public void onUpdateReceived(@NotNull Update update) {
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
    }

    public void send(@NotNull SendMessage message) {
        try {
            bot.execute(message);
            final String logText = "Telegram bot sent message";
            log.info(logText);
            log.debug(logText.concat(": ").concat(message.getText()));
        } catch (TelegramApiException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void botAnswerUtils(@NotNull String messageText, Long chatId, String userName) {
        if (!config.isDeafModeEnabled()) {
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
                    if (config.isEchoModeEnabled()) {
                        send(Message.of(chatId, messageText));
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
        send(Message.of(chatId, text));
    }

    private void sendStartMessage(Long chatId, String userName) {
        send(Message.of(chatId, "Hello, *" + userName + "*!\n" +
                "I'm a *" + config.getTelegramBotName() + "*."));
    }

    private void sendHelpMessage(Long chatId, String userName) {
        send(Message.of(chatId, "Hello, *" + userName + "*!\n" + HELP_TEXT));
    }
}
