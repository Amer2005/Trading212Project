package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.Holding;

import java.math.BigDecimal;
import java.util.UUID;

public interface HoldingService {
    void createHolding(UUID userId, String symbol, BigDecimal amount);

    void buyHolding(UUID userId, String symbol, BigDecimal amount);

    void sellHolding(UUID userId, String symbol, BigDecimal amount);

    Holding getByUserIdAndSmybol(UUID userId, String symbol);
}
