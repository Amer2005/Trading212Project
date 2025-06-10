package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.services.AuthenticationService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    private final BigDecimal STARTING_BALANCE = BigDecimal.valueOf(10000);

    private final UserDao userDao;


    public AuthenticationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User registerUser(String username, String password) {

        User newUser = User.builder()
                .username(username)
                .password(password)
                .balance(STARTING_BALANCE).build();

        userDao.create(newUser);

        User createdUser = userDao.findByUsername(username);

        return createdUser;
    }
}
