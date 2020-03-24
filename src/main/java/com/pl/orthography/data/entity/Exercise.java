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

    public Exercise() {
    }

    public Exercise(@NotNull ExerciseType exerciseType, Set<Word> words) {
        this.exerciseType = exerciseType;
        this.words = words;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public Set<Word> getWords() {
        return words;
    }

    public void setWords(Set<Word> words) {
        this.words = words;
    }

    public Set<UserExercise> getUsers() {
        return users;
    }

    public void setUsers(Set<UserExercise> users) {
        this.users = users;
    }
}