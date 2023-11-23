package com.leontev.graviton.lessons.model;

import com.leontev.graviton.bank.model.Invoice;
import com.leontev.graviton.students.model.Student;
import com.leontev.graviton.tasks.model.Homework;
import com.leontev.graviton.tutors.model.Tutor;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String topic; // Тема занятия
    private String board; // Ссылка на доску
    @ManyToOne
    private Tutor tutor;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
    private LocalDateTime dateTime;
    private LessonStatus status;
    private boolean tryLesson;
    @Enumerated(EnumType.ORDINAL)
    private LessonCancellationReason cancellationReason;
    @Enumerated(EnumType.ORDINAL)
    private LessonDuration duration;
    @ManyToOne
    private Course course;
    @OneToOne
    private Homework homework;
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    @Column(precision = 10, scale = 2)
    private BigDecimal perStudentPrice;
    @Column(precision = 10, scale = 2)
    private BigDecimal tutorEarnings;
    @ManyToMany
    private List<Invoice> invoices = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "student_lesson_rating", joinColumns = @JoinColumn(name = "lesson_id"))
    @MapKeyJoinColumn(name = "student_id")
    @Column(name = "rating")
    Map<Student, Integer> studentRating = new HashMap<>();

    private int tutorRating;

}
