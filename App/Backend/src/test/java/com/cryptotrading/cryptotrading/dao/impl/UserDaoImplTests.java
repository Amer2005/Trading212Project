package com.cryptotrading.cryptotrading.dao.impl;

import com.cryptotrading.cryptotrading.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserDaoImpl userDao;

    @Test
    public void testThatCreateUserGeneratesCorrectSQL() {

        UUID id = UUID.randomUUID();
        String username = "John";
        String password = "HASH";
        BigDecimal balance = new BigDecimal(10000);

        User user = User.builder()
                .id(id)
                .username(username)
                .password(password)
                .balance(balance)
                .build();

        userDao.create(user);

        verify(jdbcTemplate).update(eq("INSERT INTO users (username, password, balance) VALUES (?, ?, ?)"),
                eq(username), eq(password),eq(balance));
    }

    @Test
    public void testThatFindOneGeneratesTheCorrectSQL() {
        UUID id = UUID.randomUUID();

        userDao.findOne(id);

        verify(jdbcTemplate).query(
                eq("SELECT id, username, balance, session FROM users WHERE id = ? LIMIT 1"),
                ArgumentMatchers.<UserDaoImpl.UserRowMapper>any(),
                eq(id)
        );
    }
}
