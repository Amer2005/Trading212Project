package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.User;

import java.math.BigDecimal;
import java.util.UUID;

public interface UserService {
    void resetUser(UUID id);

    User getUserById(UUID id);

    boolean doesUsernameExist(String username);

    void spendMoney(UUID id, BigDecimal amount);
}
