package com.leontev.graviton.lessons.service;

import com.leontev.graviton.lessons.model.Course;
import com.leontev.graviton.lessons.model.Lesson;
import com.leontev.graviton.lessons.repo.CourseRepository;
import com.leontev.graviton.students.model.Student;
import com.leontev.graviton.students.service.StudentService;
import com.leontev.graviton.tutors.model.Tutor;
import com.leontev.graviton.tutors.service.TutorService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository repository;
    private final TutorService tutorService;
    private final StudentService studentService;

    @Override
    public Course getCourse(long id) {
        return repository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Course createCourse(String title) {
        Course course = new Course();
        course.setTitle(title);
        return saveCourse(course);
    }

    @Override
    public List<Course> getCourses(String telegramId) {
        final List<Course> courses = new ArrayList<>();

        try {
            Student student = studentService.getStudent(telegramId);
            courses.addAll(student.getCourses());
        } catch (EntityNotFoundException e) {
            Tutor tutor = tutorService.getTutor(telegramId);
            courses.addAll(tutor.getCourses());
        }

        return courses;
    }

    @Override
    @Transactional
    public Course saveCourse(Course course) {
        return repository.save(course);
    }

}
