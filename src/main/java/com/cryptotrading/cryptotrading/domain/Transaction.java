package com.cryptotrading.cryptotrading.domain;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Setter(AccessLevel.NONE)
    private String id;

    private String userId;

    private String type;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal total;

    private LocalDateTime transactionTime;
}
