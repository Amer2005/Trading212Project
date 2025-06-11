package com.cryptotrading.cryptotrading.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto {
    private Boolean status = true;

    private String errorMessage;
}
