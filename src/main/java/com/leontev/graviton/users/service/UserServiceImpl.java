package com.leontev.graviton.service;

import com.leontev.graviton.model.Student;
import com.leontev.graviton.model.User;
import com.leontev.graviton.repo.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;


    @Override
    public User getUser(String telegramId) {
        return repository.findByTelegramId(telegramId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User toggleTheme(String telegramId) {
        User user = getUser(telegramId);
        user.setDarkTheme(!user.isDarkTheme());
        return save(user);
    }

    @Transactional
    private User save(User user) {return repository.save(user);}


}
