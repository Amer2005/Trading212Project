package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.dto.response.TransactionResponseDto;
import com.cryptotrading.cryptotrading.domain.dto.response.ViewTransactionsResponseDto;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {
    TransactionResponseDto createTransaction(UUID userSession, String symbol, TransactionTypeEnum type, BigDecimal amount);

    void deleteTransaction(UUID id);

    void deleteUserTransactions(UUID userId);

    ViewTransactionsResponseDto getUserTransactions(UUID userId);
}
