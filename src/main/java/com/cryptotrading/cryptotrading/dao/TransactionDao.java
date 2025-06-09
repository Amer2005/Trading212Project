package com.cryptotrading.cryptotrading.dao;

import com.cryptotrading.cryptotrading.domain.Transaction;

public interface TransactionDao {
    void create(Transaction transaction);
}
