package com.leontev.graviton.lessons.pojo;

import com.leontev.graviton.lessons.model.Course;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;

@Data
public class CoursePojo {
    private long id;
    private String title;
    private String description;
    private boolean published;
    private List<LessonPojo> lessons;

    public CoursePojo(Course course) {
        this.id = course.getId();
        this.title = course.getTitle();
        this.description = course.getDescription();
        this.published = course.isPublished();
        this.lessons = course.getLessons().stream().map(LessonPojo::new).toList();
    }
}
