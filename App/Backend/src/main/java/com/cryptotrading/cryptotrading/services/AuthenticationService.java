package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.ResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;

import java.util.UUID;

public interface AuthenticationService {
    UserResponseDto registerUser(String username, String password);

    UserResponseDto loginUser(String username, String password);

    ResponseDto logoutUser(UUID session);
}
