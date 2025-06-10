package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.UserDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.AuthenticationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationService authenticationService;

    private final Mapper<User, UserDto> userMapper;

    public AuthController(AuthenticationService authenticationService, Mapper<User, UserDto> userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody UserDto userInfo) {
        User UserEntity = authenticationService.registerUser(userInfo.getUsername(), userInfo.getPassword());

        return userMapper.mapTo(UserEntity);
    }

    @GetMapping("/login")
    public UserDto loginUser(@RequestBody UserDto userInfo) {
        User UserEntity = authenticationService.loginUser(userInfo.getUsername(), userInfo.getPassword());

        return userMapper.mapTo(UserEntity);
    }
}
