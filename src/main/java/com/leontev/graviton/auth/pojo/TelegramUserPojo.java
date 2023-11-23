package com.leontev.graviton.model;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.User;

@Data
public class TelegramUserPojo {
    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private boolean isPremium;

    public TelegramUserPojo(User user) {
        this.id = String.valueOf(user.getId());
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUserName();
    }
}
