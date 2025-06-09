package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.dao.HoldingDao;
import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public UserDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(User user) {
        jdbcTemplate.update("INSERT INTO users (username, password, balance) VALUES (?, ?, ?)",
                user.getUsername(), user.getPassword(), user.getBalance());
    }

    @Override
    public User findOne(String id) {
        List<User> results = jdbcTemplate.query(
                "SELECT * FROM users WHERE id = ? LIMIT 1",
                new UserRowMapper(),
                id);

        return results.stream().findFirst().orElse(null);
    }

    public static class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getString("id"))
                    .username(rs.getString("username"))
                    .password(rs.getString("password"))
                    .balance(rs.getBigDecimal("balance"))
                    .build();
        }
    }
}
