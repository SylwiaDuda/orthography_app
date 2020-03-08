package com.pl.orthography.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
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
        this.accountState = AccountState.ACTIVE;
        this.exercises = new HashSet<>();
        this.tests = new HashSet<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public AccountState getAccountState() {
        return accountState;
    }

    public Set<UserExercise> getExercises() {
        return exercises;
    }

    public Set<UserTest> getTests() {
        return tests;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
