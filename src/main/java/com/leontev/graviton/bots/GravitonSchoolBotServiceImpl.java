package com.leontev.graviton.service;

import com.leontev.graviton.model.TelegramUserPojo;
import com.leontev.graviton.users.model.User;
import org.springframework.stereotype.Service;

@Service
public class GravitonSchoolBotServiceImpl implements GravitonSchoolBotService{

    private final RegistrationService registrationService;

    public GravitonSchoolBotServiceImpl(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public User register(TelegramUserPojo userPojo, String referral) {
        return registrationService.registerUser(userPojo, referral);
    }
}
