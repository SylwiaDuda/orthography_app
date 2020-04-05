package com.pl.orthography.data.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
}