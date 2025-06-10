package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.dto.request.RequestDto;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/reset")
    public void reset(@RequestBody RequestDto requestDto) {
        userService.resetUser(requestDto.getSession());
    }
}
