package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.dao.HoldingDao;
import org.springframework.jdbc.core.JdbcTemplate;

public class HoldingDaoImpl implements HoldingDao {

    private final JdbcTemplate jdbcTemplate;

    public HoldingDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
