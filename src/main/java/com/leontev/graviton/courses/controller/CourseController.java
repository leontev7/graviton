package com.leontev.graviton.lessons.controller;

import com.leontev.graviton.lessons.pojo.CoursePojo;
import com.leontev.graviton.lessons.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class CourseController {
    private final CourseService service;

    @Operation(summary = "Create course")
    @PostMapping("")
    public ResponseEntity<CoursePojo> createCourse(@RequestBody String title) {
        // TODO
        return ResponseEntity.ok(new CoursePojo(service.createCourse(title)));
    }

    @Operation(summary = "Get course by course id")
    @GetMapping("")
    public ResponseEntity<CoursePojo> getCourse(@RequestParam Long courseId) {
        return ResponseEntity.ok(new CoursePojo(service.getCourse(courseId)));
    }

    @Operation(summary = "Get all courses for user by user telegram id")
    @GetMapping("/all")
    public ResponseEntity<List<CoursePojo>> getCourses(@RequestParam String telegramId) {
        return ResponseEntity.ok(service.getCourses(telegramId).stream().map(CoursePojo::new).toList());
    }
}
