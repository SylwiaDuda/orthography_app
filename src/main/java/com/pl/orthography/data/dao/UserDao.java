package com.pl.orthography.data.dao;

import com.pl.orthography.data.entity.AccountState;
import com.pl.orthography.data.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDao extends BasicDao<User, Long> {

    User findUserByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.accountState=:state WHERE u.email=:email")
    void updateAccountStateBasedOnEmail(@Param("email") String email,@Param("state") AccountState state);
}