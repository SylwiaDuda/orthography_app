package com.pl.orthography.service;

import com.pl.orthography.data.dao.TestDao;
import com.pl.orthography.data.dto.TestTypesDto;
import com.pl.orthography.data.entity.Test;
import com.pl.orthography.data.entity.TestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}