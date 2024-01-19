package com.example.analytics.service.telegram.handler;

import org.jetbrains.annotations.NotNull;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.List;

public class ButtonHandler {

    private static final InlineKeyboardButton ANALYTIC_BUTTON = new InlineKeyboardButton("Analytic");
    private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");

    public static @NotNull InlineKeyboardMarkup inlineMarkup() {
        ANALYTIC_BUTTON.setCallbackData("/analytic");
        START_BUTTON.setCallbackData("/start");
        HELP_BUTTON.setCallbackData("/help");

        final List<InlineKeyboardButton> rowInlineFirst = List.of(ANALYTIC_BUTTON);
        final List<InlineKeyboardButton> rowInlineSecond = List.of(START_BUTTON, HELP_BUTTON);

        final List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInlineFirst, rowInlineSecond);

        final InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
