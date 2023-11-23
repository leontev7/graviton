package com.leontev.graviton.repo;

import com.leontev.graviton.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelegramId(String id);
}
