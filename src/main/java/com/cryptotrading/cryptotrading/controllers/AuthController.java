package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.request.AuthRequestDto;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;
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

    private final Mapper<User, UserResponseDto> userMapper;

    public AuthController(AuthenticationService authenticationService, UserService userService, Mapper<User, UserResponseDto> userMapper) {
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody AuthRequestDto userInfo) {

        UserResponseDto userResponse = new UserResponseDto();

        userResponse = authenticationService.registerUser(userInfo.getUsername(), userInfo.getPassword());

        if(!userResponse.getStatus())
        {
            return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);

    }

    @GetMapping("/login")
    public ResponseEntity<UserResponseDto> loginUser(@RequestBody AuthRequestDto userInfo) {

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto = authenticationService.loginUser(userInfo.getUsername(), userInfo.getPassword());

        if(!userResponseDto.getStatus()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error", userResponseDto.getErrorMessage());

            return new ResponseEntity<>(userResponseDto, headers, HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
    }
}
