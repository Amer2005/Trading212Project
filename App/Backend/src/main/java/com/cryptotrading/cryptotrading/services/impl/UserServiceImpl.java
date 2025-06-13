package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.UserResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.HoldingService;
import com.cryptotrading.cryptotrading.services.UserService;
import com.cryptotrading.cryptotrading.util.Validator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class UserServiceImpl implements UserService {

    private final BigDecimal STARTING_BALANCE = BigDecimal.valueOf(10000);

    private final Validator validator;

    private final UserDao userDao;
    private final TransactionDao transactionDao;

    private final HoldingService holdingService;

    private final Mapper<User, UserResponseDto> userMapper;

    public UserServiceImpl(Validator validator, UserDao userDao, TransactionDao transactionDao, HoldingService holdingService, Mapper<User, UserResponseDto> userMapper) {
        this.validator = validator;
        this.userDao = userDao;
        this.transactionDao = transactionDao;
        this.holdingService = holdingService;
        this.userMapper = userMapper;
    }

    @Override
    public void resetUser(UUID session) {
        if(!validator.isUUIDValid(session))
        {
            return;
        }

        User user = getUserBySession(session);

        if(user == null)
        {
            return;
        }

        user.setBalance(STARTING_BALANCE);

        holdingService.deleteUserHoldings(user.getId());
        transactionDao.deleteUserTransactions(user.getId());

        userDao.update(user);
    }

    @Override
    public User getUserById(UUID id) {
        if(!validator.isUUIDValid(id))
        {
            return null;
        }

        return  userDao.findOne(id);
    }

    @Override
    public User getUserBySession(UUID session) {
        if(!validator.isUUIDValid(session))
        {
            return null;
        }

        return userDao.findBySession(session);
    }

    @Override
    public UserResponseDto getUserDtoBySession(UUID session) {
        UserResponseDto result = new UserResponseDto();

        if(!validator.isUUIDValid(session))
        {
            result.setStatus(false);
            result.setErrorMessage("User not found!");

            return result;
        }

        User user = userDao.findBySession(session);

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
        if(!validator.isUsernameValid(username)){
            return false;
        }

        User user = userDao.findByUsername(username);

        return user != null;
    }

    @Override
    public void spendMoney(UUID session, BigDecimal amount) {
        if(!validator.isUUIDValid(session))
        {
            return;
        }

        if(!validator.isPositive(amount))
        {
            return;
        }

        User user = userDao.findBySession(session);

        if(user == null) {
            return;
        }

        BigDecimal newBalance = user.getBalance().subtract(amount);

        if(!validator.isPositive(newBalance)) {
            return;
        }

        user.setBalance(newBalance);

        userDao.update(user);
    }

    @Override
    public void increaseMoney(UUID session, BigDecimal amount) {
        if(!validator.isUUIDValid(session)) {
            return;
        }

        if(!validator.isPositive(amount)) {
            return;
        }

        User user = userDao.findBySession(session);

        if(user == null) {
            return;
        }

        BigDecimal newBalance = user.getBalance().add(amount);

        if(!validator.isPositive(newBalance)) {
            return;
        }

        user.setBalance(newBalance);

        userDao.update(user);
    }
}
