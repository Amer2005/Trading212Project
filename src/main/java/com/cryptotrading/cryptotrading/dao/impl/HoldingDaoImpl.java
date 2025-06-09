package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.dao.HoldingDao;
import com.cryptotrading.cryptotrading.domain.Holding;
import org.springframework.jdbc.core.JdbcTemplate;

public class HoldingDaoImpl implements HoldingDao {

    private final JdbcTemplate jdbcTemplate;

    public HoldingDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Holding holding) {
        jdbcTemplate.update("INSERT INTO users (user_id, symbol, amount) VALUES (?, ?, ?)",
                holding.getUserId(), holding.getSymbol(), holding.getAmount());
    }
}
