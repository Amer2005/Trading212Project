package com.cryptotrading.cryptotrading.mappers.impl;

import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.dto.TransactionDto;
import com.cryptotrading.cryptotrading.domain.dto.UserDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapperImpl implements Mapper<Transaction, TransactionDto> {

    private final ModelMapper modelMapper;

    public TransactionMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public TransactionDto mapTo(Transaction transaction) {
        return modelMapper.map(transaction, TransactionDto.class);
    }

    @Override
    public Transaction mapFrom(TransactionDto transactionDto) {
        return modelMapper.map(transactionDto, Transaction.class);
    }
}
