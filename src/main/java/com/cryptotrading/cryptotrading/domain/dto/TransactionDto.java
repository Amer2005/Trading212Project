package com.cryptotrading.cryptotrading.domain.dto;

import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDto extends ResponseDto {
    private UUID id;

    private UUID userId;

    private TransactionTypeEnum type;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal total;

    private LocalDateTime transactionTime;
}
