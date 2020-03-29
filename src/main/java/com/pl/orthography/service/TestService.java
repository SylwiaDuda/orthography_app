package com.pl.orthography.service;

import com.pl.orthography.data.dao.TestDao;
import com.pl.orthography.data.entity.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    private TestDao testDao;
    private WordService wordService;

    @Autowired
    public TestService(TestDao testDao, WordService wordService) {
        this.testDao = testDao;
        this.wordService = wordService;
    }

    public void addTest(Test test) {
        Test savedTest = testDao.saveAndFlush(test);
        savedTest.getWords()
                .forEach(word -> {
                    word.getTestsContainingWord().add(savedTest);
                    wordService.saveAndFlushWord(word);
                });
    }
}