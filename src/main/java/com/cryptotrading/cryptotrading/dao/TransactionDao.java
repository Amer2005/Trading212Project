package com.cryptotrading.cryptotrading.dao;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.User;

import java.util.UUID;

public interface TransactionDao {
    void create(Transaction transaction);

    Transaction findOne(UUID id);

    void delete(UUID id);
}
