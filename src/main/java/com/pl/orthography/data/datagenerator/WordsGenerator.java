package com.pl.orthography.data.datagenerator;

import com.pl.orthography.data.dao.WordDao;
import com.pl.orthography.data.entity.Word;
import com.pl.orthography.service.WordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WordsGenerator {

    private WordService wordService;

    @Bean
    public CommandLineRunner generateWords(WordService wordService, WordDao wordsDao) {
        this.wordService = wordService;

        return args -> {
            if (!hasData(wordsDao)) {

                ExampleWords words = new ExampleWords();

                saveWords(words.getWordsWithSingleCharacterUO());
                saveWords(words.getWordsWithDoubleCharacterUO());

                saveWords(words.getWordsWithSingleCharacterZRZ());
                saveWords(words.getWordsWithDoubleCharacterZRZ());

                saveWords(words.getWordsWithSingleCharacterHCH());
                saveWords(words.getWordsWithDoubleCharacterHCH());
            }
        };
    }

    private boolean hasData(WordDao wordDao) {
        return wordDao.count() != 0L;
    }

    private void saveWords(List<String> words) {
        words.forEach(word-> wordService.saveAndFlushWord(new Word(word)));
    }
}
