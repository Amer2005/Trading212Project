package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.ViewTransactionsResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.HoldingService;
import com.cryptotrading.cryptotrading.services.TransactionService;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {

    private final BigDecimal STARTING_BALANCE = BigDecimal.valueOf(10000);

    private final UserDao userDao;
    private final TransactionDao transactionDao;

    private final HoldingService holdingService;

    private final Mapper<User, UserResponseDto> userMapper;

    public UserServiceImpl(UserDao userDao, TransactionDao transactionDao, HoldingService holdingService, Mapper<User, UserResponseDto> userMapper) {
        this.userDao = userDao;
        this.transactionDao = transactionDao;
        this.holdingService = holdingService;
        this.userMapper = userMapper;
    }

    @Override
    public void resetUser(UUID session) {
        User user = getUserBySession(session);

        user.setBalance(STARTING_BALANCE);

        holdingService.deleteUserHoldings(user.getId());
        transactionDao.deleteUserTransactions(user.getId());

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
    public UserResponseDto getUserDtoBySession(UUID session) {
        User user = userDao.findBySession(session);
        UserResponseDto result = new UserResponseDto();

        if(user == null)
        {
            result.setStatus(false);
            result.setErrorMessage("User not found");

            return result;
        }

        result = userMapper.mapTo(userDao.findBySession(session));



        return result;
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

    @Override
    public void getMoney(UUID session, BigDecimal amount) {
        User user = userDao.findBySession(session);

        BigDecimal newBalance = user.getBalance().add(amount);

        user.setBalance(newBalance);

        userDao.update(user);
    }
}
