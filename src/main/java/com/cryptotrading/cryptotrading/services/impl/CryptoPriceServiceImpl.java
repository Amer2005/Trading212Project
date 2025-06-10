package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.services.CryptoPriceService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CryptoPriceServiceImpl implements CryptoPriceService {

    @Override
    public BigDecimal getPrice(String symbol) {
        //TO DO: Kraken API
        return BigDecimal.valueOf(1);
    }
}
