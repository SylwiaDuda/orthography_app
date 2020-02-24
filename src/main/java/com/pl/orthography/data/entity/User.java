package com.pl.orthography.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends BasicEntity {

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(unique = true)
    @NotNull
    private String email;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserRole role;

    @Column(name = "registration_date")
    @NotNull
    private Date registrationDate;

    @Column(name = "account_state")
    @Enumerated(EnumType.STRING)
    @NotNull
    private AccountState accountState;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<UserExercise> exercises = new HashSet<>();

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private Set<UserTest> tests = new HashSet<>();
}
