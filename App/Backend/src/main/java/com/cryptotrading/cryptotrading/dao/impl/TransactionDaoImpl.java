package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.dao.TransactionDao;
import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public class TransactionDaoImpl implements TransactionDao {

    private final JdbcTemplate jdbcTemplate;

    public TransactionDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Transaction transaction) {
        jdbcTemplate.update("INSERT INTO transactions (user_id, type, symbol, amount, price, total, transaction_time) VALUES (?, ?, ?, ?, ?, ?, ?)",
               transaction.getUserId(), transaction.getType().name(),
                transaction.getSymbol(), transaction.getAmount(), transaction.getPrice(),
                transaction.getTotal(), transaction.getTransactionTime());
    }

    @Override
    public Transaction findOne(UUID id) {
        List<Transaction> result = jdbcTemplate.query(
                    "SELECT * FROM transactions WHERE id = ? LIMIT 1",
                        new TransactionRowMapper(),
                        id);

        return result.stream().findFirst().orElse(null);
    }

    @Override
    public void delete(UUID id)
    {
        jdbcTemplate.update("DELETE FROM transactions WHERE id = ?", id);
    }

    @Override
    public void deleteUserTransactions(UUID userId)
    {
        jdbcTemplate.update("DELETE FROM transactions WHERE user_id = ?", userId);
    }


    public static class TransactionRowMapper implements RowMapper<Transaction> {
        public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Transaction.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .type(TransactionTypeEnum.valueOf(rs.getString("type")))
                    .symbol(rs.getString("symbol"))
                    .amount(rs.getBigDecimal("amount"))
                    .price(rs.getBigDecimal("price"))
                    .total(rs.getBigDecimal("total"))
                    .transactionTime(rs.getTimestamp("transaction_time").toLocalDateTime())
                    .build();
        }
    }


}
