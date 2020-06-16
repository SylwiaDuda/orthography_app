package com.pl.orthography.data.datagenerator;

import com.pl.orthography.data.dao.TestDao;
import com.pl.orthography.data.dao.WordDao;
import com.pl.orthography.data.entity.*;
import com.pl.orthography.service.TestService;
import com.pl.orthography.service.WordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TestGenerator {

    private TestService testService;
    private WordService wordService;

    @Bean
    public CommandLineRunner generateTests(TestService testService, WordService wordService, TestDao testDao, WordDao wordDao) {
        return args -> {

            this.testService = testService;
            this.wordService = wordService;

            if (!shouldGenerateExercise(testDao, wordDao)) {
                return;
            }

            List<Word> words = wordService.findAllWords();
            ExampleWords exampleWords = new ExampleWords();

            generateTest(TestType.TYPE_O_U_LEVEL_1, 0, words, exampleWords.getWordsWithSingleCharacterUO());
            generateTest(TestType.TYPE_O_U_LEVEL_2, 30, words, exampleWords.getWordsWithDoubleCharacterUO());

            generateTest(TestType.TYPE_Z_RZ_LEVEL_1, 10, words, exampleWords.getWordsWithSingleCharacterZRZ());
            generateTest(TestType.TYPE_Z_RZ_LEVEL_2, 40, words, exampleWords.getWordsWithDoubleCharacterZRZ());

            generateTest(TestType.TYPE_H_CH_LEVEL_1, 20, words, exampleWords.getWordsWithSingleCharacterHCH());
            generateTest(TestType.TYPE_H_CH_LEVEL_2, 50, words, exampleWords.getWordsWithDoubleCharacterHCH());
        };
    }

    private void generateTest(TestType testType, int requiredPoints, List<Word> words, List<String> exampleWords) {
        Set<Word> wordsToSave = words.stream()
                .filter(word -> exampleWords.contains(word.getWord()))
                .collect(Collectors.toSet());

        testService.addTest(new Test(requiredPoints, testType, wordsToSave));
    }

    private boolean shouldGenerateExercise(TestDao testDao, WordDao wordDao) {
        return wordDao.count() != 0L && testDao.count() == 0L;
    }
}