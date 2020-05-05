package com.pl.orthography.data.dao;

import com.pl.orthography.data.dto.TestTypesDto;
import com.pl.orthography.data.dto.UserTestDto;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserTestDao extends BasicDao<UserTest, Long> {

    @Query("SELECT MAX(ut.points) AS points FROM UserTest ut WHERE ut.user=:user AND ut.test.id=:id_test")
    Integer findGainedPointsForTest(@Param("user") User user, @Param("id_test") Long testId);

    @Query("SELECT MAX(ut.date) as date FROM UserTest ut WHERE ut.user=:user")
    LocalDateTime findLastDateTest(@Param("user") User user);

    @Query("SELECT ut.test.id as testId, ut.points as points, ut.date as date FROM UserTest ut WHERE ut.user=:user AND ut.date>:date")
    List<UserTestDto> findTestAfterDate(@Param("user") User user, @Param("date") LocalDateTime date);
}