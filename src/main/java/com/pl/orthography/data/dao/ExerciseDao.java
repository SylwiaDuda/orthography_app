package com.pl.orthography.data.dao;

import com.pl.orthography.data.dto.ExerciseTypesDto;
import com.pl.orthography.data.entity.Exercise;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseDao extends BasicDao<Exercise, Long> {

    @Query(value = "SELECT e.id as id, e.exerciseType as exerciseType FROM Exercise e")
    List<ExerciseTypesDto> findExerciseTypes();
}