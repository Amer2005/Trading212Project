package com.cryptotrading.cryptotrading.domain.dto.response;

import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponseDto extends ResponseDto {
    private UUID id;

    private UUID userId;

    private TransactionTypeEnum type;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal price;

    private BigDecimal total;

    private LocalDateTime transactionTime;
}
