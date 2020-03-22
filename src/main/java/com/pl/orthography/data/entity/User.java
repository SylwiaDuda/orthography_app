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
    private AccountStatus accountStatus;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserExercise> exercises = new HashSet<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<UserTest> tests = new HashSet<>();

    public User() {
    }

    public User(@NotNull String userName, @NotNull String email, @NotNull String password, @NotNull UserRole role, @NotNull Date registrationDate, @NotNull AccountStatus accountStatus, Set<UserExercise> exercises, Set<UserTest> tests) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registrationDate = registrationDate;
        this.accountStatus = accountStatus;
        this.exercises = exercises;
        this.tests = tests;
    }

    public User(String userName, String email, String password, UserRole role) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.registrationDate = new Date();
        this.accountStatus = AccountStatus.ACTIVE;
        this.exercises = new HashSet<>();
        this.tests = new HashSet<>();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public String getRoleName() {
        return role.name();
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Set<UserExercise> getExercises() {
        return exercises;
    }

    public void setExercises(Set<UserExercise> exercises) {
        this.exercises = exercises;
    }

    public Set<UserTest> getTests() {
        return tests;
    }

    public void setTests(Set<UserTest> tests) {
        this.tests = tests;
    }
}
