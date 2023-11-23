package com.leontev.graviton.repo;

import com.leontev.graviton.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long> {
    Optional<Tutor> findByTelegramId(String telegramId);
}
