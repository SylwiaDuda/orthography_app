package com.pl.orthography.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BasicEntity {

    @Column(name = "user_name")
    @NotNull
    private String userName;

    @Column(unique = true)
    @NotNull
    private String email;

    @Column(name = "user_password")
    @NotNull
    private String password;

    @Column(name = "user_role")
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

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserExercise> exercises = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserTest> tests = new HashSet<>();

    public User() {
    }

    public User(@NotNull String userName, @NotNull String email, @NotNull String password, @NotNull UserRole role, @NotNull Date registrationDate, @NotNull AccountState accountState, Set<UserExercise> exercises, Set<UserTest> tests) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registrationDate = registrationDate;
        this.accountState = accountState;
        this.exercises = exercises;
        this.tests = tests;
    }

    public User(String userName, String email, String password, UserRole role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registrationDate = new Date();
        this.accountState = AccountState.ACTIVE;
        this.exercises = new HashSet<>();
        this.tests = new HashSet<>();
    }
}
