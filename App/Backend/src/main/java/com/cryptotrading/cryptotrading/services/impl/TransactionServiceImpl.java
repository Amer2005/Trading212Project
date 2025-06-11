package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.domain.Holding;
import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.TransactionResponseDto;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.CryptoPriceService;
import com.cryptotrading.cryptotrading.services.HoldingService;
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
    private final HoldingService holdingService;

    private final TransactionDao transactionDao;

    private final Mapper<Transaction, TransactionResponseDto> transactionResponseMapper;

    public TransactionServiceImpl(UserService userService, CryptoPriceService cryptoPriceService, HoldingService holdingService, TransactionDao transactionDao, Mapper<Transaction, TransactionResponseDto> transactionResponseMapper) {
        this.userService = userService;
        this.cryptoPriceService = cryptoPriceService;
        this.holdingService = holdingService;
        this.transactionDao = transactionDao;
        this.transactionResponseMapper = transactionResponseMapper;
    }

    @Override
    public TransactionResponseDto createTransaction(UUID userSession, String symbol, TransactionTypeEnum type, BigDecimal amount) {

        TransactionResponseDto result = new TransactionResponseDto();

        User user = userService.getUserBySession(userSession);

        if(user == null) {

            result.setStatus(false);
            result.setErrorMessage("User not found");

            return result;
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            result.setStatus(false);
            result.setErrorMessage("Invalid amount");

            return result;
        }

        if(type == TransactionTypeEnum.BUY) {
            return createBuyTransaction(user, symbol, amount);
        }

        if(type == TransactionTypeEnum.SELL) {
            return createSellTransaction(user, symbol, amount);
        }

        result.setStatus(false);
        result.setErrorMessage("Invalid transaction type");

        return result;
    }

    private TransactionResponseDto createBuyTransaction(User user, String symbol, BigDecimal amount) {
        TransactionResponseDto result = new TransactionResponseDto();

        BigDecimal price = cryptoPriceService.getPrice(symbol);

        BigDecimal balance = user.getBalance();

        BigDecimal total = amount.multiply(price);

        if(total.compareTo(balance) == 1) {

            result.setStatus(false);
            result.setErrorMessage("Bought total exceeds user's balance!");

            return result;
        }

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

        holdingService.buyHolding(user.getId(), symbol, amount);

        transactionDao.create(transaction);

        result = transactionResponseMapper.mapTo(transaction);

        return result;
    }

    private TransactionResponseDto createSellTransaction(User user, String symbol, BigDecimal amount) {
        TransactionResponseDto result = new TransactionResponseDto();

        BigDecimal price = cryptoPriceService.getPrice(symbol);

        Holding currentHolding = holdingService.getByUserIdAndSmybol(user.getId(), symbol);

        if(currentHolding == null)
        {
            result.setStatus(false);
            result.setErrorMessage("User does not have any " + symbol);

            return result;
        }

        if(currentHolding.getAmount().compareTo(amount) == -1) {
            result.setStatus(false);
            result.setErrorMessage("User does not have enough " + symbol);
        }

        BigDecimal total = amount.multiply(price);

        userService.getMoney(user.getSession(), total);

        Transaction transaction = Transaction.builder()
                .userId(user.getId())
                .type(TransactionTypeEnum.SELL)
                .symbol(symbol)
                .amount(amount)
                .price(price)
                .total(total)
                .transactionTime(LocalDateTime.now(ZoneId.of("UTC")))
                .build();

        holdingService.sellHolding(user.getId(), symbol, amount);

        transactionDao.create(transaction);

        result = transactionResponseMapper.mapTo(transaction);

        return result;
    }

    @Override
    public void deleteTransaction(UUID id)
    {
        transactionDao.delete(id);
    }
}
