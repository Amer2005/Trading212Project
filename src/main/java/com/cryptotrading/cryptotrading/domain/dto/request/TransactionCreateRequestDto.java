package com.cryptotrading.cryptotrading.domain.dto.request;

import com.cryptotrading.cryptotrading.domain.enums.TransactionTypeEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateRequestDto extends RequestDto {
    private UUID userSession;

    private TransactionTypeEnum type;

    private String symbol;

    private BigDecimal amount;
}
