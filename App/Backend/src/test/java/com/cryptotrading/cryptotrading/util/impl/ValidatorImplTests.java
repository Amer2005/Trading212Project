package com.cryptotrading.cryptotrading.util.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class ValidatorImplTests {

    @InjectMocks
    private ValidatorImpl validator;

    @Test
    public void testIsStringForNullOrEmpty()
    {
        assertTrue(validator.isStringForNullOrEmpty(null));
        assertTrue(validator.isStringForNullOrEmpty(""));
        assertTrue(validator.isStringForNullOrEmpty("   "));
        assertFalse(validator.isStringForNullOrEmpty("Test"));
    }

    @Test
    public void testIsUsernameValid()
    {
        assertFalse(validator.isUsernameValid(null));
        assertFalse(validator.isUsernameValid(""));
        assertFalse(validator.isUsernameValid("  "));
        assertTrue(validator.isUsernameValid("Username"));
    }

    @Test
    void testIsPasswordValid() {
        assertFalse(validator.isPasswordValid(null));
        assertFalse(validator.isPasswordValid(""));
        assertTrue(validator.isPasswordValid("Password"));
    }

    @Test
    void testIsUUIDValid() {
        assertFalse(validator.isUUIDValid(null));
        assertTrue(validator.isUUIDValid(UUID.randomUUID()));
    }

    @Test
    void testIsPositive() {
        assertFalse(validator.isPositive(null));
        assertFalse(validator.isPositive(new BigDecimal(-1)));
        assertTrue(validator.isPositive(BigDecimal.ZERO));
        assertTrue(validator.isPositive(new BigDecimal(0.01)));
        assertTrue(validator.isPositive(new BigDecimal(100)));
    }

    @Test
    void testIsZero() {
        assertTrue(validator.isZero(BigDecimal.ZERO));
        assertFalse(validator.isZero(new BigDecimal(0.0001)));
        assertFalse(validator.isZero(new BigDecimal(-0.0001)));
    }
}
