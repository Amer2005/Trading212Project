package com.cryptotrading.cryptotrading.services;

public interface UserService {
    void resetUser(String id);

    public boolean doesUsernameExist(String username);
}
