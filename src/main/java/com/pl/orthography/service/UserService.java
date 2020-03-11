package com.pl.orthography.service;

import com.pl.orthography.data.dao.UserDao;
import com.pl.orthography.data.entity.AccountState;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Transactional
    public User createUserAccount(String userName, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, email, encodedPassword, UserRole.USER);
        return userDao.save(user);
    }

    @Transactional
    public User createAdminAccount(String userName, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(userName, email, encodedPassword, UserRole.ADMIN);
        return userDao.save(user);
    }

    @Transactional
    public void updateAccountState(String email, AccountState newAccountState) {
        userDao.updateAccountStateBasedOnEmail(email, newAccountState);
    }
}