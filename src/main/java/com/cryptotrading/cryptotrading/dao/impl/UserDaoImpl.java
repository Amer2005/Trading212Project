package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.dao.HoldingDao;
import com.cryptotrading.cryptotrading.dao.UserDao;
import com.cryptotrading.cryptotrading.domain.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Component
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
    public User findOne(UUID id) {
        List<User> results = jdbcTemplate.query(
                "SELECT id, username, balance, session FROM users WHERE id = ? LIMIT 1",
                new UserRowMapper(),
                id);

        return results.stream().findFirst().orElse(null);
    }

    @Override
    public User findBySession(UUID session) {
        List<User> results = jdbcTemplate.query(
                "SELECT id, username, balance, session FROM users WHERE session = ? LIMIT 1",
                new UserRowMapper(),
                session);

        return results.stream().findFirst().orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        List<User> results = jdbcTemplate.query(
                "SELECT id, username, balance, session FROM users WHERE username = ? LIMIT 1",
                new UserRowMapper(),
                username);

        return results.stream().findFirst().orElse(null);
    }

    @Override
    public String findPassword(UUID id) {
        List<String> results = jdbcTemplate.query(
                "SELECT password FROM users WHERE id = ? LIMIT 1",
                new Object[]{id},
                (rs, rowNum) -> rs.getString("password"));

        return results.stream().findFirst().orElse(null);
    }

    @Override
    public void update(User user) {
        jdbcTemplate.update("UPDATE users SET username = ?, balance = ?, session = ? WHERE id = ?",
        user.getUsername(), user.getBalance(), user.getSession(), user.getId());
    }

    public static class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(UUID.fromString(rs.getString("id")))
                    .username(rs.getString("username"))
                    .balance(rs.getBigDecimal("balance"))
                    .session(UUID.fromString(rs.getString("session")))
                    .build();
        }
    }
}
