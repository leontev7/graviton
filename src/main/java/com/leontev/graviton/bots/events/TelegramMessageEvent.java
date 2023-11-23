package com.leontev.graviton.events;

import org.springframework.context.ApplicationEvent;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class TelegramMessageEvent extends ApplicationEvent {
    public TelegramMessageEvent(SendMessage message) {
        super(message);
    }
}
