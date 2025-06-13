package com.cryptotrading.cryptotrading.util;

import java.math.BigDecimal;
import java.util.UUID;

public interface Validator {

    boolean isUsernameValid(String username);

    boolean isPasswordValid(String password);

    boolean isUUIDValid(UUID value);

    boolean isPositive(BigDecimal value);

    boolean isZero(BigDecimal value);

    boolean isStringForNullOrEmpty(String string);
}
