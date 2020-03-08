package com.pl.orthography.data.dao;

import com.pl.orthography.data.entity.User;

public interface UserDao extends BasicDao<User, Long> {

    User findUserByEmail(String email);
}