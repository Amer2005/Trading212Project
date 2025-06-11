package com.cryptotrading.cryptotrading.mappers.impl;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.dto.response.TransactionResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionResponseMapperImpl implements Mapper<Transaction, TransactionResponseDto> {

    private final ModelMapper modelMapper;

    public TransactionResponseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionResponseDto mapTo(Transaction transaction) {
        return modelMapper.map(transaction, TransactionResponseDto.class);
    }

    @Override
    public Transaction mapFrom(TransactionResponseDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }
}
