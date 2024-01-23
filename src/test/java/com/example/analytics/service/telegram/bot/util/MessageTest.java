package com.example.analytics.service.telegram.bot.util;

import com.example.analytics.service.telegram.util.Message;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import static org.assertj.core.api.Assertions.assertThat;

class MessageTest {

    @Test
    @DisplayName("Test util method 'of'.")
    void messageOfUtilTest() {
        Long chatId = 123L;
        String msg = "hillel";
        SendMessage sendMessage = Message.of(chatId, msg);
        assertThat(sendMessage.getChatId()).isEqualTo(chatId.toString());
        assertThat(sendMessage.getText()).isEqualTo(msg);
        assertThat(sendMessage.getParseMode()).isEqualTo("Markdown");
    }
}
