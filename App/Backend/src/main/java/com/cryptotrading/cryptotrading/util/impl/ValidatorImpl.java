package com.cryptotrading.cryptotrading.util.impl;

import com.cryptotrading.cryptotrading.util.Validator;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class ValidatorImpl implements Validator {

    @Override
    public boolean isStringForNullOrEmpty(String string) {
        return string == null || string.trim().isEmpty();
    }

    @Override
    public boolean isUsernameValid(String username) {
        if(isStringForNullOrEmpty(username)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isPasswordValid(String password) {
        if(isStringForNullOrEmpty(password)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean isUUIDValid(UUID value) {
        if(value == null){
            return false;
        }

        return true;
    }

    @Override
    public boolean isPositive(BigDecimal value) {
        if(value == null){
            return false;
        }

        if(value.compareTo(BigDecimal.ZERO) < 0){
            return false;
        }

        return true;
    }

    @Override
    public boolean isZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }
}
