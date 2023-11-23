package com.leontev.graviton.tasks.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "task_templates")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class TaskTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(columnDefinition = "LONGTEXT")
    private String content;

    @ElementCollection
    private List<String> options = new ArrayList<>();

    @ElementCollection
    private List<String> answers = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    private TaskType type;

    @Enumerated(EnumType.ORDINAL)
    private TaskComplexity complexity;
}
