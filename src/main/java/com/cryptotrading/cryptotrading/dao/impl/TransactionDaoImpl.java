package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.domain.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;

public class TransactionDaoImpl implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public TransactionDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Transaction transaction) {
        jdbcTemplate.update("INSERT INTO transactions (id, user_id, type, symbol, amount, price, total, timestamp) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                transaction.getId(), transaction.getUserId(), transaction.getType(),
                transaction.getSymbol(), transaction.getAmount(), transaction.getPrice(),
                transaction.getTotal(), transaction.getTimestamp());
    }
}
