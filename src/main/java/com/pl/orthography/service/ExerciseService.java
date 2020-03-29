package com.pl.orthography.service;

import com.pl.orthography.data.dao.ExerciseDao;
import com.pl.orthography.data.dto.ExerciseTypesDto;
import com.pl.orthography.data.entity.Exercise;
import com.pl.orthography.data.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExerciseService {

    private ExerciseDao exerciseDao;
    private WordService wordService;

    @Autowired
    public ExerciseService(ExerciseDao exerciseDao, WordService wordService) {
        this.exerciseDao = exerciseDao;
        this.wordService = wordService;
    }

    public void addExercise(Exercise exercise) {
        Exercise savedExercise = exerciseDao.saveAndFlush(exercise);
        savedExercise.getWords().forEach(word -> {
            word.getExerciseContainingWord().add(savedExercise);
            wordService.saveAndFlushWord(word);
        });
    }

    public List<ExerciseTypesDto> findExerciseTypes() {
        return exerciseDao.findExerciseTypes();
    }

    public List<String> findExerciseWords(Long exerciseId) {
        Optional<Exercise> exercise = exerciseDao.findById(exerciseId);

        return exercise
                .map(this::getWords)
                .orElse(Collections.emptyList());
    }

    private List<String> getWords(Exercise exercise) {
        return exercise.getWords()
                .stream()
                .map(Word::getWord)
                .collect(Collectors.toList());
    }
}