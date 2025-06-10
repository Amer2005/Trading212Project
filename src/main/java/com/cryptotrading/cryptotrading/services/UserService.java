package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.User;

import java.math.BigDecimal;
import java.util.UUID;

public interface UserService {
    void resetUser(UUID session);

    User getUserById(UUID id);

    User getUserBySession(UUID session);

    boolean doesUsernameExist(String username);

    void spendMoney(UUID session, BigDecimal amount);
}
