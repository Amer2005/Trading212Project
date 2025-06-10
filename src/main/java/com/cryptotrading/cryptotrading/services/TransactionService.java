package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;

import java.math.BigDecimal;

public interface TransactionService {
    Transaction startTransaction(String userId, String symbol, TransactionTypeEnum type, BigDecimal total);
}
