package com.cryptotrading.cryptotrading.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Setter(AccessLevel.NONE)
    private UUID id;

    private String username;

    private String password;

    private BigDecimal balance;
}