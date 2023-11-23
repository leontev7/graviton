package com.leontev.graviton.events;

import com.leontev.graviton.bots.GravitonSchoolBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
@Slf4j
@DependsOn("gravitonSchoolBot")
public class TelegramMessageEventListener implements ApplicationListener<TelegramMessageEvent> {

    private final GravitonSchoolBot gravitonSchoolBot;

    public TelegramMessageEventListener(GravitonSchoolBot gravitonSchoolBot) {
        this.gravitonSchoolBot = gravitonSchoolBot;
    }

    @Override
    public void onApplicationEvent(TelegramMessageEvent event) {
        SendMessage message = (SendMessage) event.getSource();
        gravitonSchoolBot.sendMessage(message);
    }
}
