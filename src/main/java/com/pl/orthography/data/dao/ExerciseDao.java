package com.pl.orthography.data.dao;

import com.pl.orthography.data.entity.Exercise;
import com.pl.orthography.data.entity.ExerciseType;

public interface ExerciseDao extends BasicDao<Exercise, Long> {

    Exercise findByExerciseType(ExerciseType exerciseType);
}