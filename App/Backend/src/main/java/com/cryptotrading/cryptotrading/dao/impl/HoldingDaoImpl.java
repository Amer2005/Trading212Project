package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.dao.HoldingDao;
import com.cryptotrading.cryptotrading.domain.Holding;
import com.cryptotrading.cryptotrading.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
public class HoldingDaoImpl implements HoldingDao {

    private final JdbcTemplate jdbcTemplate;

    public HoldingDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Holding holding) {
        jdbcTemplate.update("INSERT INTO holdings (user_id, symbol, amount) VALUES (?, ?, ?)",
                holding.getUserId(), holding.getSymbol(), holding.getAmount());
    }

    @Override
    public Holding findOne(UUID id) {
        List<Holding> result = jdbcTemplate.query(
                "SELECT * FROM holdings WHERE id = ? LIMIT 1",
                new HoldingRowMapper(),
                id);

        return result.stream().findFirst().orElse(null);
    }

    @Override
    public void update(Holding holding) {
        jdbcTemplate.update("UPDATE holdings SET user_id = ?, symbol = ?, amount = ? WHERE id = ?",
                holding.getUserId(), holding.getSymbol(), holding.getAmount(), holding.getId());
    }

    public Holding findByUserIdAndSymbol(UUID userId, String symbol) {
        List<Holding> result = jdbcTemplate.query(
                "SELECT * FROM holdings WHERE user_id = ? AND symbol = ? LIMIT 1",
                new HoldingRowMapper(),
                userId, symbol);

        return result.stream().findFirst().orElse(null);
    }

    @Override
    public void delete(UUID id)
    {
        jdbcTemplate.update("DELETE FROM holdings WHERE id = ?", id);
    }

    @Override
    public void deleteUserHoldings(UUID userId)
    {
        jdbcTemplate.update("DELETE FROM holdings WHERE user_id = ?", userId);
    }

    @Override
    public List<Holding> getUserHoldings(UUID userId)
    {
        return jdbcTemplate.query(
                "SELECT * FROM holdings WHERE user_id = ?",
                new HoldingRowMapper(),
                userId);
    }

    public static class HoldingRowMapper implements RowMapper<Holding> {
        public Holding mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Holding.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .userId(UUID.fromString(rs.getString("user_id")))
                    .symbol(rs.getString("symbol"))
                    .amount(rs.getBigDecimal("amount"))
                    .build();
        }
    }
}
