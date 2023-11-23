package com.leontev.graviton.pojo;

import com.leontev.graviton.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserPojo {
    private String telegramId;
    private String firstName;
    private String lastName;
    private String username;
    private boolean darkTheme;
    private int role;

    public UserPojo(User user) {
        this.telegramId = user.getTelegramId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
        this.darkTheme = user.isDarkTheme();
        this.role = user.getRole().ordinal();
    }
}
