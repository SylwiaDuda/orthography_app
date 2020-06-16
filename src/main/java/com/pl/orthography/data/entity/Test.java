package com.pl.orthography.data.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "tests")
public class Test extends BasicEntity {

    @Column(name = "required_points")
    @NotNull
    private int requiredPoints;

    @Column(name = "test_type")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TestType testType;

    @ManyToMany(mappedBy = "testsContainingWord", fetch = FetchType.EAGER)
    private Set<Word> words;

    @OneToMany(mappedBy = "test", fetch = FetchType.EAGER)
    private Set<UserTest> users = new HashSet<>();

    public Test() {
    }

    public Test(@NotNull int requiredPoints, @NotNull TestType testType, Set<Word> words) {
        this.requiredPoints = requiredPoints;
        this.testType = testType;
        this.words = words;

    }

    public Test(@NotNull int requiredPoints, @NotNull TestType testType, Set<Word> words, Set<UserTest> users) {
        this.requiredPoints = requiredPoints;
        this.testType = testType;
        this.words = words;
        this.users = users;
    }
}