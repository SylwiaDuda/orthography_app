package com.pl.orthography.data.datagenerator;

import com.pl.orthography.data.dao.UserDao;
import com.pl.orthography.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserGenerator {

    @Bean
    public CommandLineRunner generateUsers(UserService userService, UserDao userDao) {
        return args -> {

            if (hasData(userDao)) {
                return;
            }

            userService.createAdminAccount("admin","admin@admin.pl","admin");
            userService.createUserAccount("user","user@user.pl","user");
        };
    }

    private boolean hasData(UserDao userDAO) {
        return userDAO.count() != 0L;
    }
}