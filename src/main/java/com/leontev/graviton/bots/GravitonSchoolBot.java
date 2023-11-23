package com.leontev.graviton;

import com.leontev.graviton.model.TelegramUserPojo;
import com.leontev.graviton.service.GravitonSchoolBotService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.invoices.SendInvoice;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.EntityType;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.payments.LabeledPrice;
import org.telegram.telegrambots.meta.api.objects.payments.SuccessfulPayment;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class GravitonSchoolBot extends TelegramLongPollingBot {

    private String botUsername;
    private String botToken;

    @Autowired
    private GravitonSchoolBotService botService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update != null && update.hasMessage()) {
            Message message = update.getMessage();

            if (message != null && message.hasEntities()) {
                var entities = message.getEntities();

                for (var entity: entities) {
                    if (entity.getType().equals(EntityType.BOTCOMMAND)) {
                        String command = entity.getText();

                        switch (command) {
                            case ("/start") -> {

                                // Если есть реферальный код от другого пользователя
                                String referral = null;
                                if (message.hasText() && message.getText().split(" +").length > 1)
                                    referral = message.getText().split(" +")[1];

                                var registeredUser = botService.register(new TelegramUserPojo(message.getFrom()), referral);
                            }
                            case ("/pay") -> {
                                sendInvoice(SendInvoice.builder()
                                        .chatId(message.getChatId())
                                        .currency("RUB")
                                        .providerToken("381764678:TEST:67903")
                                        .title("Тест")
                                        .startParameter("wrwret")
                                        .description("Тест")
                                        .payload("Тест")
                                        .price(new LabeledPrice("Тест",300000))
                                        .build());
                            }
                            case ("/rate") -> {
                                List<InlineKeyboardButton> keyboardRow = new ArrayList<>();
                                String[] emojis = {"\uD83D\uDE1E", "\uD83D\uDE1F", "\uD83D\uDE10", "\uD83D\uDE42", "\uD83D\uDE04"};

                                for (String emoji : emojis) {
                                    InlineKeyboardButton button = new InlineKeyboardButton();
                                    button.setText(emoji);
                                    button.setCallbackData("rate_" + emoji);  // Устанавливаем callback_data для обработки ответа пользователя
                                    keyboardRow.add(button);
                                }

                                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
                                keyboard.add(keyboardRow);
                                InlineKeyboardMarkup keyboardMarkup = new InlineKeyboardMarkup();
                                keyboardMarkup.setKeyboard(keyboard);

                                // Отправка сообщения
                                SendMessage sendMessage = new SendMessage();
                                sendMessage.setChatId(message.getChatId());
                                sendMessage.setText("Занятие завершено.\nОцените занятие:");
                                sendMessage.setReplyMarkup(keyboardMarkup);

                                sendMessage(sendMessage);
                            }
                            default -> {}
                        }
                    }
                }
            }
        }
    }

    public void sendMessage(SendMessage message) {
        try {
            executeAsync(message);
        } catch (TelegramApiException e) {
            log.error("error while sending message");
        }
    }

    private Message sendInvoice(SendInvoice invoice) {
        try {
            return execute(invoice);
        } catch (TelegramApiException e) {
            log.error("error while sending invoice");
        }
        return null;
    }
}
