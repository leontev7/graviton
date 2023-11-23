package com.leontev.graviton.service;

import com.leontev.graviton.model.TelegramUserPojo;
import com.leontev.graviton.users.model.User;

public interface GravitonSchoolBotService {
    User register(TelegramUserPojo userPojo, String referral);
}
