package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import com.cryptotrading.cryptotrading.services.CryptoPriceService;
import com.cryptotrading.cryptotrading.services.TransactionService;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final CryptoPriceService cryptoPriceService;

    private final TransactionDao transactionDao;

    public TransactionServiceImpl(UserService userService, CryptoPriceService cryptoPriceService, TransactionDao transactionDao) {
        this.userService = userService;
        this.cryptoPriceService = cryptoPriceService;
        this.transactionDao = transactionDao;
    }

    private Transaction createTransaction(Transaction transaction) {
        return null;
    }

    @Override
    public Transaction startTransaction(String userId, String symbol, TransactionTypeEnum type, BigDecimal total) {

        User user = userService.getUserById(userId);

        if(user == null) {
            return null;
        }

        if(type == TransactionTypeEnum.BUY) {
            return createBuyTransaction(user, symbol, total);
        }

        if(type == TransactionTypeEnum.SELL) {
            //return createBuyTransaction(user, symbol, total);
        }

        return null;
    }

    private Transaction createBuyTransaction(User user, String symbol, BigDecimal total) {
        BigDecimal price = cryptoPriceService.getPrice(symbol);

        BigDecimal balance = user.getBalance();

        if(total.compareTo(balance) == 1) {
            return null;
        }

        BigDecimal amount = total.divide(price, 8, RoundingMode.FLOOR);

        userService.spendMoney(user.getId(), total);

        Transaction transaction = Transaction.builder()
                .userId(user.getId())
                .type(TransactionTypeEnum.BUY)
                .symbol(symbol)
                .amount(amount)
                .price(price)
                .total(total)
                .transactionTime(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        transactionDao.create(transaction);

        return transaction;
    }

    private Transaction createSellTransaction(User user, String symbol, BigDecimal total) {
        BigDecimal price = cryptoPriceService.getPrice(symbol);

        BigDecimal amount = total.divide(price, 8, RoundingMode.FLOOR);

        userService.spendMoney(user.getId(), total);

        Transaction transaction = Transaction.builder()
                .userId(user.getId())
                .type(TransactionTypeEnum.BUY)
                .symbol(symbol)
                .amount(amount)
                .price(price)
                .total(total)
                .transactionTime(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        transactionDao.create(transaction);

        return transaction;
    }
}
