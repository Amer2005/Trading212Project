package com.cryptotrading.cryptotrading.mappers.impl;

import com.cryptotrading.cryptotrading.domain.Holding;
import com.cryptotrading.cryptotrading.domain.Transaction;
import com.cryptotrading.cryptotrading.domain.dto.response.HoldingDto;
import com.cryptotrading.cryptotrading.domain.dto.response.TransactionResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HoldingMapperImpl implements Mapper<Holding, HoldingDto> {

    private final ModelMapper modelMapper;

    public HoldingMapperImpl(ModelMapper modelMapper)
    {
        this.modelMapper = modelMapper;
    }

    @Override
    public HoldingDto mapTo(Holding holding) {
        return modelMapper.map(holding, HoldingDto.class);
    }

    @Override
    public Holding mapFrom(HoldingDto holdingDto) {
        return modelMapper.map(holdingDto, Holding.class);
    }
}
