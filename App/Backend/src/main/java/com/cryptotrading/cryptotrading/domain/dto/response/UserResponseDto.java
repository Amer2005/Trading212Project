package com.cryptotrading.cryptotrading.domain.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto extends ResponseDto {

    private UUID id;

    private String username;

    private String password;

    private BigDecimal balance;

    private UUID session;
}
