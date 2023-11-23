package com.leontev.graviton.lessons.model;

import com.leontev.graviton.students.model.Student;
import com.leontev.graviton.tutors.model.Tutor;
import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String title;
    private String description;
    private boolean published = false;
    @ManyToOne
    private Tutor tutor;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Student> students = new HashSet<>();
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();
}

