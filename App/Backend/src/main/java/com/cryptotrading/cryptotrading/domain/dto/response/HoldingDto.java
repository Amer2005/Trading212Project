package com.cryptotrading.cryptotrading.domain.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoldingDto {
    private UUID id;

    private UUID userId;

    private String symbol;

    private BigDecimal amount;
}
