package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.User;

import java.math.BigDecimal;

public interface UserService {
    void resetUser(String id);

    User getUserById(String id);

    boolean doesUsernameExist(String username);

    void spendMoney(String id, BigDecimal amount);
}
