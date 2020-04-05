package com.pl.orthography.service;

import com.pl.orthography.data.dao.UserTestDao;
import com.pl.orthography.data.dto.TestDto;
import com.pl.orthography.data.entity.Test;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Transactional
    public List<TestDto> findUserTests(String email) {
        User user = userService.findUserByEmail(email);

        return testService.findTestTypes()
                .stream()
                .map(TestDto::new)
                .peek(testDto -> testDto.setGainedPoints(findGainedPointsForTest(user, testDto.getId())))
                .collect(Collectors.toList());
    }

    private Integer findGainedPointsForTest(User user, Long testId) {
        Integer points = userTestDao.findGainedPointsForTest(user, testId);

        return points == null ? 0 : points;
    }
}