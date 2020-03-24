package com.pl.orthography.data.dao;

        import com.pl.orthography.data.entity.Word;
        import org.springframework.data.jpa.repository.Query;

        import java.util.List;


public interface WordDao extends BasicDao<Word, Long> {

    @Query("SELECT w FROM Word w WHERE w.word like '%ó%' OR w.word like '%u%'")
    List<Word> findWordsContainsOU();

    @Query("SELECT w FROM Word w WHERE w.word like '%ż%' OR w.word like '%rz%'")
    List<Word> findWordsContainsRZ();

    @Query("SELECT w FROM Word w WHERE w.word like '%h%'")
    List<Word> findWordsContainsH();
}