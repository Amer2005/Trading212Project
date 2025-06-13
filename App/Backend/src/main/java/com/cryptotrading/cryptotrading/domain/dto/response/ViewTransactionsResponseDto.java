package com.cryptotrading.cryptotrading.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ViewTransactionsResponseDto extends ResponseDto {

    public ViewTransactionsResponseDto() {
        transactions = new ArrayList<>();
    }

    private List<TransactionResponseDto> transactions;
}
