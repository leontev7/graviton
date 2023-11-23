package com.leontev.graviton.model;

import com.leontev.graviton.bank.model.BankAccount;
import com.leontev.graviton.users.model.User;
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
public class Student extends User {
    private int points;

    private int grade;
    @ManyToMany(mappedBy = "students", cascade = CascadeType.ALL)
    private List<Lesson> lessons = new ArrayList<>();

    @OneToOne
    private BankAccount bankAccount;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Student> invitedStudents = new ArrayList<>();

    @ManyToOne
    private Student invitedBy;
}

