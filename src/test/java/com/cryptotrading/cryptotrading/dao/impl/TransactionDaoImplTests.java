package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private TransactionDaoImpl transactionDao;

    @Test
    public void testThatCreateTransactionGeneratesCorrectSQL() {
        String userId = "GUID";
        TransactionTypeEnum type = TransactionTypeEnum.BUY;
        String symbol = "AAPL";
        BigDecimal amount = new BigDecimal("10.00000000");
        BigDecimal price = new BigDecimal("150.00000000");
        BigDecimal total = new BigDecimal("1500.00000000");
        LocalDateTime transactionTime = LocalDateTime.now();

        Transaction transaction = Transaction.builder()
                .userId(userId)
                .type(type)
                .symbol(symbol)
                .amount(amount)
                .price(price)
                .total(total)
                .transactionTime(transactionTime)
                .build();

        transactionDao.create(transaction);

        verify(jdbcTemplate).update(eq(
                        "INSERT INTO transactions (user_id, type, symbol, amount, price, total, transaction_time) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"),
                eq(userId), eq(type.name()), eq(symbol), eq(amount), eq(price), eq(total), eq(transactionTime));
    }
}
