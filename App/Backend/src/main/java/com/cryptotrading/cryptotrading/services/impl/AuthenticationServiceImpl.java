package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.ResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.AuthenticationService;
import com.cryptotrading.cryptotrading.services.UserService;
import com.cryptotrading.cryptotrading.util.Validator;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class AuthenticationServiceImpl implements AuthenticationService {

    private final BigDecimal STARTING_BALANCE = BigDecimal.valueOf(10000);

    private final UserDao userDao;

    private final Validator validator;

    private final UserService userService;

    private final Mapper<User, UserResponseDto> userMapper;

    public AuthenticationServiceImpl(UserDao userDao, Validator validator, UserService userService, Mapper<User, UserResponseDto> userMapper) {
        this.userDao = userDao;
        this.validator = validator;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDto registerUser(String username, String password) {

        UserResponseDto result = new UserResponseDto();

        if(!validator.isUsernameValid(username)) {
            result.setStatus(false);
            result.setErrorMessage("Invalid Username!");

            return result;
        }

        if(!validator.isPasswordValid(password)) {
            result.setStatus(false);
            result.setErrorMessage("Invalid Password!");

            return result;
        }

        if(userService.doesUsernameExist(username)) {
            result.setStatus(false);

            result.setErrorMessage("User with that username already exists!");

            return result;
        }

        User newUser = User.builder()
                .username(username)
                .password(password)
                .balance(STARTING_BALANCE).build();

        userDao.create(newUser);

        User createdUser = userDao.findByUsername(username);

        return userMapper.mapTo(createdUser);
    }

    @Override
    public UserResponseDto loginUser(String username, String password) {
        UserResponseDto result = new UserResponseDto();

        if(!validator.isUsernameValid(username)) {
            result.setStatus(false);
            result.setErrorMessage("Invalid Username!");

            return result;
        }

        if(!validator.isPasswordValid(password)) {
            result.setStatus(false);
            result.setErrorMessage("Invalid Password!");

            return result;
        }

        if(!userService.doesUsernameExist(username)) {

            result.setStatus(false);
            result.setErrorMessage("Wrong username or password!");

            return result;
        }

        User user = userDao.findByUsername(username);

        if(user == null)
        {
            result.setStatus(false);
            result.setErrorMessage("User not found!");

            return result;
        }

        String DBPassword = userDao.findPassword(user.getId());

        if(DBPassword.equals(password)){

            user.setSession(UUID.randomUUID());

            userDao.update(user);

            return userMapper.mapTo(user);
        }
        else {
            result.setStatus(false);
            result.setErrorMessage("Wrong username or password!");

            return result;
        }
    }

    @Override
    public ResponseDto logoutUser(UUID session) {
        ResponseDto result = new ResponseDto();

        if(!validator.isUUIDValid(session)) {
            result.setStatus(false);
            result.setErrorMessage("Invalid session!");
            return result;
        }

        User user = userDao.findBySession(session);

        if(user != null){

            user.setSession(UUID.randomUUID());

            userDao.update(user);

            return userMapper.mapTo(user);
        }
        else {
            result.setStatus(false);
            result.setErrorMessage("User not found");

            return result;
        }
    }
}
