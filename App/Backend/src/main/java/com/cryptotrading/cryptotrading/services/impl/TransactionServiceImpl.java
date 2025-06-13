package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.domain.Holding;
import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.User;
import com.cryptotrading.cryptotrading.domain.dto.response.TransactionResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.ViewTransactionsResponseDto;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.CryptoPriceService;
import com.cryptotrading.cryptotrading.services.HoldingService;
import com.cryptotrading.cryptotrading.services.TransactionService;
import com.cryptotrading.cryptotrading.services.UserService;
import com.cryptotrading.cryptotrading.util.Validator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionServiceImpl implements TransactionService {

    private final UserService userService;
    private final CryptoPriceService cryptoPriceService;
    private final HoldingService holdingService;

    private final Validator validator;

    private final TransactionDao transactionDao;

    private final Mapper<Transaction, TransactionResponseDto> transactionResponseMapper;

    public TransactionServiceImpl(UserService userService, CryptoPriceService cryptoPriceService, HoldingService holdingService, Validator validator, TransactionDao transactionDao, Mapper<Transaction, TransactionResponseDto> transactionResponseMapper) {
        this.userService = userService;
        this.cryptoPriceService = cryptoPriceService;
        this.holdingService = holdingService;
        this.validator = validator;
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

        if(validator.isStringForNullOrEmpty(symbol))
        {
            result.setStatus(false);
            result.setErrorMessage("Incorrect crypto symbol");

            return result;
        }

        if(!validator.isPositive(amount)) {
            result.setStatus(false);
            result.setErrorMessage("Amount cannot be negative");

            return result;
        }

        if(validator.isZero(amount)) {
            result.setStatus(false);
            result.setErrorMessage("Amount cannot be zero");

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

        if(!validator.isPositive(BigDecimal.ZERO)) {
            result.setStatus(false);
            result.setErrorMessage("Crypto not found");

            return result;
        }

        BigDecimal balance = user.getBalance();

        BigDecimal total = amount.multiply(price);

        if(total.compareTo(balance) > 0) {

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

        if(!validator.isPositive(price)) {
            result.setStatus(false);
            result.setErrorMessage("Crypto not found");

            return result;
        }

        Holding currentHolding = holdingService.getByUserIdAndSymbol(user.getId(), symbol);

        if(currentHolding == null)
        {
            result.setStatus(false);
            result.setErrorMessage("User does not have any " + symbol);

            return result;
        }

        if(currentHolding.getAmount().compareTo(amount) < 0) {
            result.setStatus(false);
            result.setErrorMessage("User does not have enough " + symbol);

            return  result;
        }

        BigDecimal total = amount.multiply(price);

        userService.increaseMoney(user.getSession(), total);

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
    public void deleteTransaction(UUID id) {
        if(!validator.isUUIDValid(id))
        {
            return;
        }

        transactionDao.delete(id);
    }

    @Override
    public void deleteUserTransactions(UUID userId) {
        if(!validator.isUUIDValid(userId))
        {
            return;
        }

        transactionDao.deleteUserTransactions(userId);
    }

    @Override
    public ViewTransactionsResponseDto getUserTransactions(UUID userId)
    {
        ViewTransactionsResponseDto result = new ViewTransactionsResponseDto();

        if(!validator.isUUIDValid(userId))
        {
            result.setStatus(false);
            result.setErrorMessage("User not found");

            return result;
        }

        List<TransactionResponseDto> transactionResponseDtos = result.getTransactions();

        List<Transaction> transactions = transactionDao.getUserTransactions(userId);

        if(transactions == null)
        {
            result.setStatus(false);
            result.setErrorMessage("No transactions found");

            return result;
        }

        for (Transaction transaction : transactions) {
            transactionResponseDtos.add(transactionResponseMapper.mapTo(transaction));
        }

        return result;
    }

}
