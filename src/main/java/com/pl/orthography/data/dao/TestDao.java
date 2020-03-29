package com.pl.orthography.data.dao;

import com.pl.orthography.data.dto.TestTypesDto;
import com.pl.orthography.data.entity.Test;
import com.pl.orthography.data.entity.TestType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestDao extends BasicDao<Test, Long> {

    @Query(value = "SELECT t.id as id, t.requiredPoints as requiredPoints, t.testType as testType FROM Test t")
    List<TestTypesDto> findTestTypes();

    @Query("SELECT t FROM Test t WHERE t.testType=:testType")
    Test findByTestType(@Param("testType") TestType testType);
}