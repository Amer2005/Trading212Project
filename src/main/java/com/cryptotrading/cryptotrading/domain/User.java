package com.cryptotrading.cryptotrading.domain;

import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Setter(AccessLevel.NONE)
    private Long id;

    private String username;

    private String password;

    private BigDecimal balance;

}
