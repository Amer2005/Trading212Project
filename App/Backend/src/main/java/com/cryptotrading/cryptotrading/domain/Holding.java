package com.cryptotrading.cryptotrading.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Holding {

    @Setter(AccessLevel.NONE)
    private UUID id;

    private UUID userId;

    private String symbol;

    private BigDecimal amount;
}
