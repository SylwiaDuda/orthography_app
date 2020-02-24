package com.pl.orthography.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "exercises")
public class Exercise extends BasicEntity {

    @Enumerated(EnumType.STRING)
    @NotNull
    private ExerciseType exerciseType;

    @ManyToMany(mappedBy = "exerciseContainingWord", fetch = FetchType.EAGER)
    private Set<Word> words = new HashSet<>();

    @OneToMany(mappedBy = "exercise", fetch = FetchType.EAGER)
    private Set<UserExercise> users = new HashSet<>();
}