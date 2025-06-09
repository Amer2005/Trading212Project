package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.domain.Holding;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class HoldingDaoImpltTests {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private HoldingDaoImpl holdingDao;

    @Test
    public void testThatCreateHoldingGeneratesCorrectSQL() {
        String userId = "GUID";
        String symbol = "BTC";
        BigDecimal amount = new BigDecimal("2.50000000");

        Holding holding = Holding.builder()
                .userId(userId)
                .symbol(symbol)
                .amount(amount)
                .build();

        holdingDao.create(holding);

        verify(jdbcTemplate).update(eq(
                        "INSERT INTO holdings (user_id, symbol, amount) VALUES (?, ?, ?)"),
                eq(userId), eq(symbol), eq(amount));
    }
}
