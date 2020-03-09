package com.pl.orthography.service;

import com.pl.orthography.data.dao.UserDao;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserDao userDao;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao userDao, PasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    public User findUserByEmailAndPassword(String email, String password) {
        User user = userDao.findUserByEmail(email);
        if (passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    @Transactional
    public User createUserAccount(String userName, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, email, encodedPassword, UserRole.USER);
        return userDao.save(user);
    }

    @Transactional
    public void createAdminAccount(String userName, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, email, encodedPassword, UserRole.ADMIN);
        User createdUsed = userDao.save(user);
    }
}