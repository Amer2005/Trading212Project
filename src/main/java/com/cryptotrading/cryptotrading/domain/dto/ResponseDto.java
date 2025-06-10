package com.cryptotrading.cryptotrading.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private Boolean status;

    private String errorMessage;
}
