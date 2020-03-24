package com.pl.orthography.data.datagenerator;

import com.pl.orthography.data.dao.ExerciseDao;
import com.pl.orthography.data.dao.WordDao;
import com.pl.orthography.data.entity.Exercise;
import com.pl.orthography.data.entity.ExerciseType;
import com.pl.orthography.data.entity.Word;
import com.pl.orthography.service.ExerciseService;
import com.pl.orthography.service.WordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ExerciseGenerator {

    private ExerciseService exerciseService;
    private WordService wordService;

    @Bean
    public CommandLineRunner generateExercises(ExerciseService exerciseService, WordService wordService, ExerciseDao exerciseDao, WordDao wordDao) {
        return args -> {

            this.exerciseService = exerciseService;
            this.wordService = wordService;

            if (!shouldGenerateExercise(exerciseDao, wordDao)) {
                return;
            }

            List<Word> words = wordService.findAllWords();
            generateExercise(ExerciseType.TYPE_O_U_LEVEL_1, findWordsWithSingleCharacter(words, "[óu]"));
            generateExercise(ExerciseType.TYPE_O_U_LEVEL_2, findWordsWithDoubleCharacter(words, "[óu]"));

            generateExercise(ExerciseType.TYPE_Z_RZ_LEVEL_1, findWordsWithSingleCharacter(words, "ż|rz"));
            generateExercise(ExerciseType.TYPE_Z_RZ_LEVEL_2, findWordsWithDoubleCharacter(words, "ż|rz"));

            generateExercise(ExerciseType.TYPE_H_CH_LEVEL_1, findWordsWithSingleCharacter(words, "h"));
            generateExercise(ExerciseType.TYPE_H_CH_LEVEL_2, findWordsWithDoubleCharacter(words, "h"));
        };
    }

    private boolean shouldGenerateExercise(ExerciseDao exerciseDao, WordDao wordDao) {
        return wordDao.count() != 0L && exerciseDao.count() == 0L;
    }

    private void generateExercise(ExerciseType exerciseType, Set<Word> words) {
        Exercise exercise = new Exercise(exerciseType, words);
        exerciseService.addExercise(exercise);
    }

    private Set<Word> findWordsWithSingleCharacter(List<Word> words, String regex) {
        return words.stream()
                .filter(word -> wordContainsSingleCharacter(word.getWord(), regex))
                .collect(Collectors.toSet());
    }

    private boolean wordContainsSingleCharacter(String word, String regex) {
        return word.split(regex).length == 2;
    }

    private Set<Word> findWordsWithDoubleCharacter(List<Word> words, String regex) {
        return words.stream()
                .filter(word -> wordContainsDoubleCharacter(word.getWord(), regex))
                .collect(Collectors.toSet());
    }

    private boolean wordContainsDoubleCharacter(String word, String regex) {
        return word.split(regex).length == 3;
    }

    private Set<Word> findWordsWith3Character(List<Word> words, String regex) {
        return words.stream()
                .filter(word -> wordContainsDoubleCharacter(word.getWord(), regex))
                .collect(Collectors.toSet());
    }

    private boolean wordContains3Character(String word, String regex) {
        return word.split(regex).length >3;
    }
}