package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.HoldingDao;
import com.cryptotrading.cryptotrading.domain.Holding;
import com.cryptotrading.cryptotrading.domain.dto.response.HoldingDto;
import com.cryptotrading.cryptotrading.domain.dto.response.ViewHoldingsResponseDto;
import com.cryptotrading.cryptotrading.mappers.Mapper;
import com.cryptotrading.cryptotrading.services.HoldingService;
import com.cryptotrading.cryptotrading.util.Validator;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class HoldingServiceImpl implements HoldingService {

    private final HoldingDao holdingDao;
    private final Mapper<Holding, HoldingDto> holdingMapper;

    private final Validator validator;

    public HoldingServiceImpl(HoldingDao holdingDao, Mapper<Holding, HoldingDto> holdingMapper, Validator validator) {
        this.holdingDao = holdingDao;
        this.holdingMapper = holdingMapper;
        this.validator = validator;
    }


    @Override
    public void buyHolding(UUID userId, String symbol, BigDecimal amount) {
        Holding holding = holdingDao.findByUserIdAndSymbol(userId, symbol);

        if(!validator.isUUIDValid(userId))
        {
            return;
        }

        if(validator.isStringForNullOrEmpty(symbol))
        {
            return;
        }

        if(!validator.isPositive(amount))
        {
            return;
        }

        if(holding == null) {
            createHolding(userId, symbol, amount);

            return;
        }

        BigDecimal newAmount = holding.getAmount().add(amount);

        if(!validator.isPositive(newAmount))
        {
            return;
        }

        holding.setAmount(newAmount);

        holdingDao.update(holding);
    }

    @Override
    public void sellHolding(UUID userId, String symbol, BigDecimal amount) {
        if(!validator.isUUIDValid(userId)) {
            return;
        }

        Holding holding = holdingDao.findByUserIdAndSymbol(userId, symbol);

        if(holding == null) {
            return;
        }

        if(!validator.isPositive(amount)) {
            return;
        }

        if(validator.isZero(amount)) {
            return;
        }

        BigDecimal newAmount = holding.getAmount().subtract(amount);

        if(validator.isZero(newAmount)) {
            holdingDao.delete(holding.getId());
            return;
        }

        if(!validator.isPositive(newAmount)) {
            return;
        }

        holding.setAmount(newAmount);

        holding.setAmount(newAmount);

        holdingDao.update(holding);
    }

    @Override
    public Holding getByUserIdAndSymbol(UUID userId, String symbol) {
        if(!validator.isUUIDValid(userId)) {
            return null;
        }

        if(validator.isStringForNullOrEmpty(symbol)) {
            return null;
        }

        return holdingDao.findByUserIdAndSymbol(userId, symbol);
    }

    @Override
    public void deleteUserHoldings(UUID userId) {
        if(!validator.isUUIDValid(userId))
        {
            return;
        }

        holdingDao.deleteUserHoldings(userId);
    }

    @Override
    public ViewHoldingsResponseDto getUserHoldings(UUID userId)
    {
        ViewHoldingsResponseDto result =  new ViewHoldingsResponseDto();

        if(!validator.isUUIDValid(userId)) {
            result.setStatus(false);
            result.setErrorMessage("Invalid User");

            return result;
        }

        List<Holding> holdingList = holdingDao.getUserHoldings(userId);

        if(holdingList == null) {
            result.setStatus(false);
            result.setErrorMessage("There was an error getting holdings");

            return result;
        }

        List<HoldingDto> holdingDtoList = result.getHoldings();

        for(Holding holding : holdingList) {
            HoldingDto holdingDto = holdingMapper.mapTo(holding);

            holdingDtoList.add(holdingDto);
        }

        return  result;
    }

    @Override
    public void createHolding(UUID userId, String symbol, BigDecimal amount) {
        if(!validator.isUUIDValid(userId)) {
            return;
        }

        if(validator.isStringForNullOrEmpty(symbol)) {
            return;
        }

        if(!validator.isPositive(amount)) {
            return;
        }

        Holding holding = Holding.builder()
                .userId(userId)
                .symbol(symbol)
                .amount(amount)
                .build();

        holdingDao.create(holding);
    }
}
