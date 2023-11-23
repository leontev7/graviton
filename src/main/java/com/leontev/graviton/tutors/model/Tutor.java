package com.leontev.graviton.model;

import com.leontev.graviton.bank.model.BankAccount;
import com.leontev.graviton.users.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tutor extends User {
    @Column(columnDefinition = "TEXT")
    private String description;

    @ElementCollection
    @CollectionTable(name = "tutor_price_list", joinColumns = @JoinColumn(name = "tutor_id"))
    @MapKeyColumn(name = "title")
    @Column(name = "price")
    Map<String, Integer> priceList = new HashMap<>();

    private boolean shared = false;

    @OneToMany(mappedBy = "tutor")
    private List<Lesson> lessons = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tutor_trial_lesson_intervals", joinColumns = @JoinColumn(name = "tutor_id"))
    private List<TutorTrialLessonInterval> trialLessonsTimeIntervals = new ArrayList<>();

    @OneToOne
    private BankAccount bankAccount;
}
