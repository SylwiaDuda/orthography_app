package com.pl.orthography.service;

import com.pl.orthography.data.dao.ExerciseDao;
import com.pl.orthography.data.entity.Exercise;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExerciseService {

    private ExerciseDao exerciseDao;
    private WordService wordService;

    @Autowired
    public ExerciseService(ExerciseDao exerciseDao, WordService wordService) {
        this.exerciseDao = exerciseDao;
        this.wordService = wordService;
    }

    public List<Exercise> findAllExercise() {
        return exerciseDao.findAll();
    }

    public void addExercise(Exercise exercise) {
        Exercise savedExercise = exerciseDao.saveAndFlush(exercise);
        savedExercise.getWords().forEach(word -> {
            word.getExerciseContainingWord().add(savedExercise);
            wordService.saveAndFlushWord(word);
        });
    }
}