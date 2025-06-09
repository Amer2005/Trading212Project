package com.cryptotrading.cryptotrading.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Holding {

    @Setter(AccessLevel.NONE)
    private String id;

    private String userId;

    private String symbol;

    private BigDecimal amount;
}
