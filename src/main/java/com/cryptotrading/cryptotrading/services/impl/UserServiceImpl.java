package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class UserServiceImpl implements UserService {

    private final BigDecimal STARTING_BALANCE = BigDecimal.valueOf(10000);

    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }



    @Override
    public void resetUser(String id) {
        User user = userDao.findOne(id);

        user.setBalance(STARTING_BALANCE);

        userDao.udpate(user);
        //TO DO: delete Holdings and Transactions
    }

    @Override
    public User getUserById(String id) {
        return  userDao.findOne(id);
    }

    @Override
    public boolean doesUsernameExist(String username) {
        User user = userDao.findByUsername(username);

        return user != null;
    }

    @Override
    public void spendMoney(String id, BigDecimal amount) {
        User user = userDao.findOne(id);

        BigDecimal newBalance = user.getBalance().subtract(amount);

        user.setBalance(newBalance);

        userDao.udpate(user);
    }
}
