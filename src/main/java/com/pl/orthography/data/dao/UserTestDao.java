package com.pl.orthography.data.dao;

import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTestDao extends BasicDao<UserTest, Long> {

    @Query("SELECT MAX(ut.points) as points FROM UserTest ut WHERE ut.user=:user and ut.test.id=:id_test")
    Integer findGainedPointsForTest(@Param("user") User user, @Param("id_test") Long testId);
}