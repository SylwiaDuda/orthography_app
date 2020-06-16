package com.pl.orthography.data.datagenerator;

import com.pl.orthography.data.dao.TestDao;
import com.pl.orthography.data.dao.UserDao;
import com.pl.orthography.data.dao.UserTestDao;
import com.pl.orthography.data.entity.Test;
import com.pl.orthography.data.entity.TestType;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.service.TestService;
import com.pl.orthography.service.UserService;
import com.pl.orthography.service.UserTestService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserTestGenerator {

    @Bean
    public CommandLineRunner generateUserTests(UserTestService userTestService, UserTestDao userTestDao,
                                               UserService userService, UserDao userDao,
                                               TestService testService, TestDao testDao) {
        return args -> {

            if (!shouldGenerateUserTest(userTestDao, userDao, testDao)) {
                return;
            }

            User user = userService.findUserByEmail("admin@admin.pl");
            Test testWithOU = testService.findByTestType(TestType.TYPE_O_U_LEVEL_1);
            Test testWithZRZ = testService.findByTestType(TestType.TYPE_Z_RZ_LEVEL_1);

            userTestService.saveUserTest(user, testWithOU, 5, LocalDateTime.now());
            userTestService.saveUserTest(user, testWithOU, 10, LocalDateTime.now());

            userTestService.saveUserTest(user, testWithZRZ, 6, LocalDateTime.now());
            userTestService.saveUserTest(user, testWithZRZ, 11, LocalDateTime.now());
        };
    }

    private boolean shouldGenerateUserTest(UserTestDao userTestDao, UserDao userDao, TestDao testDao) {
        return userTestDao.count() == 0L && userDao.count() != 0L && testDao.count() != 0L;
    }
}