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
    public User findOne(String id) {
        List<User> results = jdbcTemplate.query(
                "SELECT id, username, balance FROM users WHERE id = ? LIMIT 1",
                new UserRowMapper(),
                UUID.fromString(id));

        return results.stream().findFirst().orElse(null);
    }

    @Override
    public User findByUsername(String username) {
        List<User> results = jdbcTemplate.query(
                "SELECT id, username, balance FROM users WHERE username = ? LIMIT 1",
                new UserRowMapper(),
                username);

        return results.stream().findFirst().orElse(null);
    }

    @Override
    public String findPassword(String id) {

        UUID uuid = UUID.fromString(id);

        List<String> results = jdbcTemplate.query(
                "SELECT password FROM users WHERE id = ? LIMIT 1",
                new Object[]{uuid},
                (rs, rowNum) -> rs.getString("password"));

        return results.stream().findFirst().orElse(null);
    }

    @Override
    public void udpate(User user) {
        jdbcTemplate.update("UPDATE users SET username = ?, balance = ? WHERE id = ?",
        user.getUsername(), user.getBalance(), UUID.fromString(user.getId()));
    }

    public static class UserRowMapper implements RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return User.builder()
                    .id(rs.getString("id"))
                    .username(rs.getString("username"))
                    .balance(rs.getBigDecimal("balance"))
                    .build();
        }
    }
}
