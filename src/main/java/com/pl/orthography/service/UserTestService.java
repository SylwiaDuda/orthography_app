package com.pl.orthography.service;

import com.pl.orthography.data.dao.UserTestDao;
import com.pl.orthography.data.dto.TestDto;
import com.pl.orthography.data.dto.UserTestDto;
import com.pl.orthography.data.entity.AccountStatus;
import com.pl.orthography.data.entity.Test;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserTest;
import com.pl.orthography.exception.TestException;
import com.pl.orthography.exception.UserException;
import com.pl.orthography.rest.testontroller.jsontemplates.TestResultDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
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

    public void saveResult(String email, TestResultDto testResultDto) throws UserException, TestException {
        User user = userService.findUserByEmail(email);

        if (user == null) {
            throw new UserException("User with e-mail address doesn't exist: " + email);
        }

        Optional<Test> test = testService.findById(testResultDto.getTestId());
        if (test.isPresent()) {
            saveUserTest(user, test.get(), testResultDto.getGainedPoints(), testResultDto.getDate());
        } else {
            throw new TestException("Test with ths id doesn't exist: " + testResultDto.getTestId());
        }
    }

    public void saveUserTest(User user, Test test, int points, LocalDateTime date) {
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

    public LocalDateTime findLastDateTest(String email) {
        return userTestDao.findLastDateTest(userService.findUserByEmail(email));
    }

    public List<UserTestDto> findTestAfterDate(String email, LocalDateTime dateTime) {
        return userTestDao.findTestAfterDate(userService.findUserByEmail(email), dateTime);
    }

    public TreeMap<String, Long> getDatesToNumberOfTests() {
        List<LocalDateTime> registrationDates = userTestDao.getAllTestDates();
        List<String> dates = new ArrayList<>();
        registrationDates.forEach(date -> dates.add(date.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
        Map<String, Long> result = dates.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        return new TreeMap<>(result);
    }
}