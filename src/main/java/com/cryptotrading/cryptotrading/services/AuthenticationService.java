package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.User;

public interface AuthenticationService {
    User registerUser(String username, String password);

    User loginUser(String username, String password);
}
