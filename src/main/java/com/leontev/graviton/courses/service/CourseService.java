package com.leontev.graviton.lessons.service;

import com.leontev.graviton.lessons.model.Course;

import java.util.Collection;
import java.util.List;

public interface CourseService {

    Course getCourse(long id);
    Course createCourse(String title);
    Course saveCourse(Course course);
    List<Course> getCourses(String telegramId);
}
