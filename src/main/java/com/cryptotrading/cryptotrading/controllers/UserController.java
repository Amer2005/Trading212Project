package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.dto.ResetUserRequestDto;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reset")
    public void reset(@RequestBody ResetUserRequestDto resetUserRequestDto) {
        userService.resetUser(resetUserRequestDto.getId());
    }
}
