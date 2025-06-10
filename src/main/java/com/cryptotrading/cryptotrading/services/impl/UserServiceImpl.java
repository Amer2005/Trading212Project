package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {

    private final BigDecimal STARTING_BALANCE = BigDecimal.valueOf(10000);

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }



    @Override
    public void resetUser(UUID session) {
        User user = getUserBySession(session);

        user.setBalance(STARTING_BALANCE);

        userDao.update(user);
    }

    @Override
    public User getUserById(UUID id) {
        return  userDao.findOne(id);
    }

    @Override
    public User getUserBySession(UUID session)
    {
        return userDao.findBySession(session);
    }

    @Override
    public boolean doesUsernameExist(String username) {
        User user = userDao.findByUsername(username);

        return user != null;
    }

    @Override
    public void spendMoney(UUID session, BigDecimal amount) {
        User user = userDao.findBySession(session);

        BigDecimal newBalance = user.getBalance().subtract(amount);

        user.setBalance(newBalance);

        userDao.update(user);
    }
}
