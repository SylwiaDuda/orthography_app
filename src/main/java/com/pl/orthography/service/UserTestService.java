package com.pl.orthography.service;

import com.pl.orthography.data.dao.UserTestDao;
import com.pl.orthography.data.entity.Test;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserTestService {

    private UserTestDao userTestDao;
    private TestService testService;
    private UserService userService;

    @Autowired
    public UserTestService(UserTestDao userTestDao, TestService testService, UserService userService) {
        this.userTestDao = userTestDao;
        this.testService = testService;
        this.userService = userService;
    }

    public void saveUserTest(User user, Test test, int points, Date date) {
        UserTest userTest = new UserTest(user, test, points, date);
        UserTest savedUserTest = userTestDao.saveAndFlush(userTest);

        test.getUsers().add(savedUserTest);
        testService.save(test);

        user.getTests().add(savedUserTest);
        userService.save(user);
    }
}