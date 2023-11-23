package com.leontev.graviton.lessons.repo;

import com.leontev.graviton.lessons.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
