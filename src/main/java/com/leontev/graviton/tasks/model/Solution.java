package com.leontev.graviton.tasks.model;

import com.leontev.graviton.students.model.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task extends TaskTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int attemptsCount;

    private boolean solved;

    @ManyToOne
    private Student student;
}
