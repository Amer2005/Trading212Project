package com.cryptotrading.cryptotrading.dao;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.User;

public interface TransactionDao {
    void create(Transaction transaction);

    Transaction findOne(String id);
}
