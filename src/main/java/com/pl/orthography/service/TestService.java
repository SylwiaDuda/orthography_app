package com.pl.orthography.service;

import com.pl.orthography.data.dao.TestDao;
import com.pl.orthography.data.dto.TestTypesDto;
import com.pl.orthography.data.entity.Exercise;
import com.pl.orthography.data.entity.Test;
import com.pl.orthography.data.entity.TestType;
import com.pl.orthography.data.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService {
    private TestDao testDao;
    private WordService wordService;

    @Autowired
    public TestService(TestDao testDao, WordService wordService) {
        this.testDao = testDao;
        this.wordService = wordService;
    }

    public Test save(Test test) {
        return testDao.saveAndFlush(test);
    }

    public void addTest(Test test) {
        Test savedTest = testDao.saveAndFlush(test);
        savedTest.getWords()
                .forEach(word -> {
                    word.getTestsContainingWord().add(savedTest);
                    wordService.saveAndFlushWord(word);
                });
    }

    public List<TestTypesDto> findTestTypes() {
        return testDao.findTestTypes();
    }

    public Test findByTestType(TestType type) {
        return testDao.findByTestType(type);
    }

    public Optional<Test> findById(Long id) {
        return testDao.findById(id);
    }

    public List<String> findETestWords(Long testId) {
        Optional<Test> test = testDao.findById(testId);

        return test
                .map(this::getWords)
                .orElse(Collections.emptyList());
    }

    private List<String> getWords(Test test) {
        return test.getWords()
                .stream()
                .map(Word::getWord)
                .collect(Collectors.toList());
    }
}