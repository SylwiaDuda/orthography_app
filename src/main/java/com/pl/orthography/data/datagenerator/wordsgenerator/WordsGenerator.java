package com.pl.orthography.data.datagenerator.wordsgenerator;

import com.pl.orthography.data.dao.WordDao;
import com.pl.orthography.data.entity.Word;
import com.pl.orthography.service.WordService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class WordsGenerator {

    private String PATH = "C:\\Users\\sylwi\\Desktop\\orthography\\orthography\\src\\main\\java\\com\\pl\\orthography\\data\\datagenerator\\wordsgenerator\\slowa.csv";

    @Bean
    public CommandLineRunner generateWords(WordService wordService, WordDao wordsDao) {
        return args -> {
            if (!hasData(wordsDao)) {
                Set<String> wordsList = new HashSet<>();
                InputStreamReader fileReader = new InputStreamReader(new FileInputStream(PATH), "Cp1250");
                try (BufferedReader br = new BufferedReader(fileReader)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String word = line.split("/Z|/V")[0];
                        wordsList.add(word);
                    }
                }

                Set<String> wordsWithO = wordsList.stream()
                        .filter(word -> word.contains("ó"))
                        .collect(Collectors.toSet());

                Set<String> wordsWithU = wordsList.stream()
                        .filter(word -> word.contains("u"))
                        .collect(Collectors.toSet());

                Set<String> wordsWithRZ = wordsList.stream()
                        .filter(word -> word.contains("rz"))
                        .collect(Collectors.toSet());

                Set<String> wordsWithZ = wordsList.stream()
                        .filter(word -> word.contains("ż"))
                        .collect(Collectors.toSet());

                Set<String> wordsWithHCH = wordsList.stream()
                        .filter(word -> word.contains("h"))
                        .collect(Collectors.toSet());

                Set<String> allWords = new HashSet<>();
                allWords.addAll(wordsWithO);
                allWords.addAll(wordsWithU);
                allWords.addAll(wordsWithRZ);
                allWords.addAll(wordsWithZ);
                allWords.addAll(wordsWithHCH);

                allWords.forEach(word -> wordService.addWord(new Word(word)));
            }
        };
    }

    private boolean hasData(WordDao wordDao) {
        return wordDao.count() != 0L;
    }
}