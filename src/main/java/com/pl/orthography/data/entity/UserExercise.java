package com.pl.orthography.data.entity;

import javax.persistence.*;

@Entity
@Table(name = "user_exercises")
public class UserExercise extends BasicEntity{


    @ManyToOne
    @JoinColumn(name="id_user")
    private User user;

    @ManyToOne
    @JoinColumn(name="id_exercise")
    private Exercise exercise;

    @Column(name = "exerise_state")
    @Enumerated(EnumType.STRING)
    private ExerciseType exerciseType;
}
