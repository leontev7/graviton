package com.leontev.graviton.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String telegramId;
    private String firstName;
    private String lastName;
    private String username;
    private boolean darkTheme;
    @Enumerated(EnumType.ORDINAL)
    private Role role;
}
