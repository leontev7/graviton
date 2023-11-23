package com.leontev.graviton.repo;

import com.leontev.graviton.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByTelegramId(String id);
}
