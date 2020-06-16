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
            ExampleWords exampleWords = new ExampleWords();

            generateExercise(ExerciseType.TYPE_O_U_LEVEL_1, words, exampleWords.getWordsWithSingleCharacterUO());
            generateExercise(ExerciseType.TYPE_O_U_LEVEL_2, words, exampleWords.getWordsWithDoubleCharacterUO());

            generateExercise(ExerciseType.TYPE_Z_RZ_LEVEL_1, words, exampleWords.getWordsWithSingleCharacterZRZ());
            generateExercise(ExerciseType.TYPE_Z_RZ_LEVEL_2, words, exampleWords.getWordsWithDoubleCharacterZRZ());

            generateExercise(ExerciseType.TYPE_H_CH_LEVEL_1, words, exampleWords.getWordsWithSingleCharacterHCH());
            generateExercise(ExerciseType.TYPE_H_CH_LEVEL_2, words, exampleWords.getWordsWithDoubleCharacterHCH());
        };
    }

    private void generateExercise(ExerciseType exerciseType, List<Word> words, List<String> exampleWords) {
        Set<Word> wordsToSave = words.stream()
                .filter(word -> exampleWords.contains(word.getWord()))
                .collect(Collectors.toSet());

        exerciseService.addExercise(new Exercise(exerciseType, wordsToSave));
    }

    private boolean shouldGenerateExercise(ExerciseDao exerciseDao, WordDao wordDao) {
        return wordDao.count() != 0L && exerciseDao.count() == 0L;
    }
}