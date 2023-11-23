package com.leontev.graviton.service;

import com.leontev.graviton.model.Student;
import com.leontev.graviton.repo.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public Student getStudent(String telegramId) {
        return repository.findByTelegramId(telegramId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Student> getStudents(String telegramId) {
        return repository.findAll();
    }

}
