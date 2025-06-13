package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.dto.request.TransactionCreateRequestDto;
import com.cryptotrading.cryptotrading.domain.dto.response.TransactionResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.TransactionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transaction/create")
    public ResponseEntity<TransactionResponseDto> startBuyTransaction(@RequestBody TransactionCreateRequestDto transactionInfo) {
        TransactionResponseDto transaction = transactionService.createTransaction(transactionInfo.
                getUserSession(),
                transactionInfo.getSymbol(),
                transactionInfo.getType(),
                transactionInfo.getAmount());

        if(!transaction.getStatus()) {
            return new ResponseEntity<>(transaction, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
