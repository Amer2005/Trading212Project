package com.cryptotrading.cryptotrading.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Holding {

    private Long userId;

    private String symbol;

    private BigDecimal amount;
}
