package com.leontev.graviton.controller;

import com.leontev.graviton.pojo.StudentPojo;
import com.leontev.graviton.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @PostMapping("/student")
    public ResponseEntity<StudentPojo> getStudent(@RequestBody String telegramId) {
        return ResponseEntity.ok(new StudentPojo(service.getStudent(telegramId)));
    }

    @PostMapping("/students")
    public ResponseEntity<List<StudentPojo>> getStudents(@RequestBody String telegramId) {
        return ResponseEntity.ok(service.getStudents(telegramId).stream().map(StudentPojo::new).collect(Collectors.toList()));
    }
}
