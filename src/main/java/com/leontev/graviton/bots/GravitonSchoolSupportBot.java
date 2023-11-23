package com.leontev.graviton;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@Getter
@Setter
public class GravitonSchoolSupportBot extends TelegramLongPollingBot {

    private String botUsername;
    private String botToken;
    private String adminId;

    @Override
    public void onUpdateReceived(Update update) {
        if (update != null && update.hasMessage()) {
            Message message = update.getMessage();
            if (message != null && message.hasText()) {
                sendMessage(SendMessage.builder().chatId(adminId).text(message.getText()).build());
                sendMessage(SendMessage.builder().chatId(message.getChatId()).text("Сообщение отправлено администратору школы. Спасибо!").build());
            }
        }
    }

    private void sendMessage(SendMessage message) {
        try {
            executeAsync(message);
            log.info("message sent");
        } catch (TelegramApiException e) {
            log.error("error while sending message");
        }
    }
}
