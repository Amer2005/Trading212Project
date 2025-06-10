package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.UserDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.AuthenticationService;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthenticationService authenticationService;

    private final UserService userService;

    private final Mapper<User, UserDto> userMapper;

    public AuthController(AuthenticationService authenticationService, UserService userService, Mapper<User, UserDto> userMapper) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userInfo) {

        boolean userExists = userService.doesUsernameExist(userInfo.getUsername());

        if(userExists) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Repeating-Username-Error", "Username already exists");

            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        User UserEntity = authenticationService.registerUser(userInfo.getUsername(), userInfo.getPassword());

        return new ResponseEntity<>(userMapper.mapTo(UserEntity), HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<UserDto> loginUser(@RequestBody UserDto userInfo) {

        User UserEntity = authenticationService.loginUser(userInfo.getUsername(), userInfo.getPassword());

        if(UserEntity == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Invalid-Login-Error", "Wrong username or password");

            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userMapper.mapTo(UserEntity), HttpStatus.OK);
    }
}
