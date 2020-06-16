package com.pl.orthography.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "words")
public class Word extends BasicEntity {

    @Column(unique = true)
    @NotNull
    private String word;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "test_words",
            joinColumns = {@JoinColumn(name = "id_word")},
            inverseJoinColumns = {@JoinColumn(name = "id_test")}
    )
    private Set<Test> testsContainingWord= new HashSet<>(0);

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "exercise_words",
            joinColumns = {@JoinColumn(name = "id_word")},
            inverseJoinColumns = {@JoinColumn(name = "id_exercise")}
    )
    private Set<Exercise> exerciseContainingWord = new HashSet<>(0);

    public Word() {
    }

    public Word(@NotNull String word) {
        this.word = word;
    }

    public Word(@NotNull String word, Set<Test> testsContainingWord, Set<Exercise> exerciseContainingWord) {
        this.word = word;
        this.testsContainingWord = testsContainingWord;
        this.exerciseContainingWord = exerciseContainingWord;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Set<Test> getTestsContainingWord() {
        return testsContainingWord;
    }

    public void setTestsContainingWord(Set<Test> testsContainingWord) {
        this.testsContainingWord = testsContainingWord;
    }

    public Set<Exercise> getExerciseContainingWord() {
        return exerciseContainingWord;
    }

    public void setExerciseContainingWord(Set<Exercise> exerciseContainingWord) {
        this.exerciseContainingWord = exerciseContainingWord;
    }
}