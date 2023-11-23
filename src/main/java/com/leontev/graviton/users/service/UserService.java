package com.leontev.graviton.service;

import com.leontev.graviton.model.User;

public interface UserService {
    User getUser(String telegramId);
    User toggleTheme(String telegramId);
}
