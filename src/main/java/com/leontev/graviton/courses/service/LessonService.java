package com.leontev.graviton.lessons.service;

import com.leontev.graviton.lessons.model.Lesson;
import com.leontev.graviton.lessons.pojo.LessonCancellationRequest;
import com.leontev.graviton.lessons.pojo.LessonCreateRequest;
import com.leontev.graviton.lessons.pojo.LessonPojo;
import com.leontev.graviton.tutors.model.Tutor;
import com.leontev.graviton.tutors.pojo.TutorPojo;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonService {

    Lesson createLesson(LessonCreateRequest request);
    Lesson getLesson(Long id);
    List<Lesson> getLessons(String telegramId);
    Lesson updateLesson(LessonPojo lessonPojo);
    Lesson cancelLesson(LessonCancellationRequest request);
}
