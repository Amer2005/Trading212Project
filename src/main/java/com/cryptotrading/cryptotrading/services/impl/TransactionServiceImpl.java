package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.TransactionResponseDto;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.CryptoPriceService;
import com.cryptotrading.cryptotrading.services.TransactionService;
import com.cryptotrading.cryptotrading.services.UserService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final CryptoPriceService cryptoPriceService;

    private final TransactionDao transactionDao;

    private final Mapper<Transaction, TransactionResponseDto> transactionResponseMapper;

    public TransactionServiceImpl(UserService userService, CryptoPriceService cryptoPriceService, TransactionDao transactionDao, Mapper<Transaction, TransactionResponseDto> transactionResponseMapper) {
        this.userService = userService;
        this.cryptoPriceService = cryptoPriceService;
        this.transactionDao = transactionDao;
        this.transactionResponseMapper = transactionResponseMapper;
    }

    @Override
    public TransactionResponseDto createTransaction(UUID userSession, String symbol, TransactionTypeEnum type, BigDecimal total) {

        TransactionResponseDto result = new TransactionResponseDto();

        User user = userService.getUserBySession(userSession);

        if(user == null) {

            result.setStatus(false);
            result.setErrorMessage("User not found");

            return result;
        }

        if(type == TransactionTypeEnum.BUY) {
            return createBuyTransaction(user, symbol, total);
        }

        if(type == TransactionTypeEnum.SELL) {
            //return createSellTransaction(user, symbol, total);
        }

        result.setStatus(false);
        result.setErrorMessage("Invalid transaction type");

        return result;
    }

    private TransactionResponseDto createBuyTransaction(User user, String symbol, BigDecimal total) {
        TransactionResponseDto result = new TransactionResponseDto();

        BigDecimal price = cryptoPriceService.getPrice(symbol);

        BigDecimal balance = user.getBalance();

        if(total.compareTo(balance) == 1) {

            result.setStatus(false);
            result.setErrorMessage("Bought total exceeds user's balance!");

            return result;
        }

        BigDecimal amount = total.divide(price, 8, RoundingMode.FLOOR);

        userService.spendMoney(user.getSession(), total);

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

        result = transactionResponseMapper.mapTo(transaction);

        return result;
    }

    private TransactionResponseDto createSellTransaction(User user, String symbol, BigDecimal total) {
        //TO DO

        return null;
    }
}
