package com.pl.orthography.service;

import com.pl.orthography.data.dao.WordDao;
import com.pl.orthography.data.entity.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class WordService {

    private WordDao wordDao;

    @Autowired
    public WordService(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Transactional
    public void addWord(Word word) {
        wordDao.save(word);
    }

    public List<Word> findAllWords() {
        return wordDao.findAll();
    }

    public List<Word> findWordsContainsOU() {
        return wordDao.findWordsContainsOU();
    }

    public List<Word> findWordsContainsRZ() {
        return wordDao.findWordsContainsRZ();
    }

    public List<Word> findWordsContainsH() {
        return wordDao.findWordsContainsH();
    }

    public void saveAndFlushWord(Word word) {
        wordDao.saveAndFlush(word);
    }
}