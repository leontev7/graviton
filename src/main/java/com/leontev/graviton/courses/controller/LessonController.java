package com.leontev.graviton.lessons.controller;


import com.leontev.graviton.lessons.pojo.LessonCancellationRequest;
import com.leontev.graviton.lessons.pojo.LessonCreateRequest;
import com.leontev.graviton.lessons.pojo.LessonPojo;
import com.leontev.graviton.lessons.service.LessonService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lesson")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RequiredArgsConstructor
public class LessonController {

    private final LessonService service;

    @Operation(summary = "Create lesson")
    @PostMapping("")
    public ResponseEntity<LessonPojo> createLesson(@RequestBody LessonCreateRequest request) {
        return ResponseEntity.ok(new LessonPojo(service.createLesson(request)));
    }

    @Operation(summary = "Get lesson by lesson id")
    @GetMapping("")
    public ResponseEntity<LessonPojo> getLesson(@RequestParam Long lessonId) {
        return ResponseEntity.ok(new LessonPojo(service.getLesson(lessonId)));
    }

    @Operation(summary = "Update lesson")
    @PatchMapping("")
    public ResponseEntity<LessonPojo> updateLesson(@RequestBody LessonPojo lessonPojo) {
        return ResponseEntity.ok(new LessonPojo(service.updateLesson(lessonPojo)));
    }

    @Operation(summary = "Cancel lesson")
    @PatchMapping("/cancel")
    public ResponseEntity<LessonPojo> cancelLesson(@RequestBody LessonCancellationRequest request) {
        return ResponseEntity.ok(new LessonPojo(service.cancelLesson(request)));
    }

    @Operation(summary = "Get all lessons for user by user telegram id")
    @GetMapping("/all")
    public ResponseEntity<List<LessonPojo>> getLessons(@RequestParam String telegramId) {
        return ResponseEntity.ok(service.getLessons(telegramId).stream().map(LessonPojo::new).toList());
    }
}
