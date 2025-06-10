package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransactionService {
    Transaction startTransaction(UUID userId, String symbol, TransactionTypeEnum type, BigDecimal total);
}
