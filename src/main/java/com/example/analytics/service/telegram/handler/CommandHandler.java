package com.example.analytics.service.telegram.handler;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

public interface CommandHandler {

    String HELP_TEXT = "I can sent to you analyse some crypto pairs based on google, twitter and coin market data.\n\n" +
            "The following commands are available to you:\n\n" +
            "/analytic - get actual analytic data\n" +
            "/start - start the bot\n" +
            "/help - help menu";

    List<BotCommand> LIST_OF_COMMANDS = List.of(
            new BotCommand("/analytic", "analytic data"),
            new BotCommand("/start", "start bot"),
            new BotCommand("/help", "bot info")
    );
}
