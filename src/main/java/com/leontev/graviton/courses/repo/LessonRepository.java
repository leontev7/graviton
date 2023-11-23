package com.leontev.graviton.lessons.repo;

import com.leontev.graviton.lessons.model.Lesson;
import com.leontev.graviton.lessons.model.LessonStatus;
import com.leontev.graviton.tutors.model.Tutor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByTutorAndDateTimeBetween(Tutor tutor, LocalDateTime start, LocalDateTime end);
    List<Lesson> findByStatus(LessonStatus status);
}
