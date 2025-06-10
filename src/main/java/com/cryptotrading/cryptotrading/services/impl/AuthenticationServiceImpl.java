package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.services.AuthenticationService;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    private final BigDecimal STARTING_BALANCE = BigDecimal.valueOf(10000);

    private final UserDao userDao;

    private final UserService userService;

    public AuthenticationServiceImpl(UserDao userDao, UserService userService) {
        this.userDao = userDao;
        this.userService = userService;
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

    @Override
    public User loginUser(String username, String password) {
        if(!userService.doesUsernameExist(username)) {
            return null;
        }

        User user = userDao.findByUsername(username);

        String DBPassword = userDao.findPassword(user.getId());

        if(DBPassword.equals(password)){
            return user;
        }
        else {
            return null;
        }
    }
}
