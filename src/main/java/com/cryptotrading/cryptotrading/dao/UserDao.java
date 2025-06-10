package com.cryptotrading.cryptotrading.dao;

import com.cryptotrading.cryptotrading.domain.User;

public interface UserDao {
    void create(User user);

    User findOne(String id);

    User findByUsername(String username);

    String findPassword(String id);
}
