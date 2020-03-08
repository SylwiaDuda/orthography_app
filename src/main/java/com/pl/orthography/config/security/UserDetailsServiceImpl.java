package com.pl.orthography.config.security;

import com.pl.orthography.data.dao.UserDao;
import com.pl.orthography.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Primary
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDao userDao;

    @Autowired
    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findUserByEmail(email);
        if (null == user) {
            throw new UsernameNotFoundException("No user present with username: " + email);
        } else {
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name())));
        }
    }
}