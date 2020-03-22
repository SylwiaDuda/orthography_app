package com.pl.orthography.service;

import com.pl.orthography.data.dao.UserDao;
import com.pl.orthography.data.entity.AccountStatus;
import com.pl.orthography.data.entity.User;
import com.pl.orthography.data.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    public void updateAccountState(String email, AccountStatus newAccountStatus) {
        userDao.updateAccountStateBasedOnEmail(email, newAccountStatus);
    }

    public TreeMap<String, Long> getDatesToNumberOfRegistration() {
        List<Date> registrationDates = userDao.getAllRegistrationDates();
        List<String> dates = new ArrayList<>();
        registrationDates.forEach(date -> dates.add(new SimpleDateFormat("dd/MM/yyyy").format(date)));
        Map<String, Long> result =
                dates.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );

        return new TreeMap<>(result);
    }

    public Map<AccountStatus, Long> getStatusToNumberOfAccounts() {
        return userDao.getAllAccountsStatus()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }
}