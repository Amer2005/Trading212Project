package com.cryptotrading.cryptotrading.controllers;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.dto.TransactionDto;
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

    private final Mapper<Transaction, TransactionDto> transactionMapper;

    public TransactionController(TransactionService transactionService, Mapper<Transaction, TransactionDto> transactionMapper) {
        this.transactionService = transactionService;
        this.transactionMapper = transactionMapper;
    }

    @PostMapping("/buy")
    public ResponseEntity<TransactionDto> startBuyTransaction(@RequestBody TransactionDto transactionInfo) {
        Transaction transaction = transactionService.startTransaction(transactionInfo.
                getUserId(),
                transactionInfo.getSymbol(),
                transactionInfo.getType(),
                transactionInfo.getTotal());

        if(transaction == null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Error-During-Transaction", "An error has occurred during transaction");

            return new ResponseEntity<>(null, headers, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(transactionMapper.mapTo(transaction), HttpStatus.CREATED);
    }
}
