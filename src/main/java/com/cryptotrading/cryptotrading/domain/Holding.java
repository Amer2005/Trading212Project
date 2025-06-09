package com.cryptotrading.cryptotrading.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Holding {

    private Long userId;

    private String symbol;

    private BigDecimal amount;
}
