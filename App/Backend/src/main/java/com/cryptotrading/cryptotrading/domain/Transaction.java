package com.cryptotrading.cryptotrading.domain;

import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    @Setter(AccessLevel.NONE)
    private UUID id;

    private UUID userId;

    private TransactionTypeEnum type;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal total;

    private LocalDateTime transactionTime;
}
