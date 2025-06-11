package com.cryptotrading.cryptotrading.dao;

import com.cryptotrading.cryptotrading.domain.User;

import java.util.UUID;

public interface UserDao {

    void create(User user);

    User findOne(UUID id);

    User findBySession(UUID session);

    User findByUsername(String username);

    String findPassword(UUID id);

    void update(User user);
}
