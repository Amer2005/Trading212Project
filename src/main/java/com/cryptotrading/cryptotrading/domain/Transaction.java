package com.cryptotrading.cryptotrading.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private Long id;

    private Long userId;

    private String type;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal total;

    private LocalDateTime timestamp;
}
