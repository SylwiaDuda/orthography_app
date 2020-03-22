package com.pl.orthography.service;

import com.pl.orthography.data.dao.WordsDao;
import com.pl.orthography.data.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class WordService {

    private WordsDao wordsDao;

    @Autowired
    public WordService(WordsDao wordsDao) {
        this.wordsDao = wordsDao;
    }

    @Transactional
    public void addWord(Word word) {
        wordsDao.save(word);
    }
}