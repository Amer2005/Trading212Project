package com.cryptotrading.cryptotrading.services;

import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;

import java.math.BigDecimal;

public interface CryptoPriceService {
    BigDecimal getPrice(String symbol);
}
