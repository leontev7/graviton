package com.leontev.graviton.service;

import com.leontev.graviton.model.Student;

import java.util.List;

public interface StudentService {
    Student getStudent(String telegramId);
    List<Student> getStudents(String telegramId);
}
