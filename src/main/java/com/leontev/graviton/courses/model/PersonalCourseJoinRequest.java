package com.leontev.graviton.courses.model;

import com.leontev.graviton.students.model.Student;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class CourseJoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Student student;
    @ManyToOne
    private PersonalCourse course;
}
