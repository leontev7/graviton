package com.leontev.graviton.tasks.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany
    private List<Task> tasks = new ArrayList<>();

    @OneToMany
    private List<Solution> solutions = new ArrayList<>();
}
