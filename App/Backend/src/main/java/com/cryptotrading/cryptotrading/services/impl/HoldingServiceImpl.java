package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.dao.HoldingDao;
import com.cryptotrading.cryptotrading.domain.Holding;
import com.cryptotrading.cryptotrading.services.HoldingService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class HoldingServiceImpl implements HoldingService {

    private final HoldingDao holdingDao;

    public HoldingServiceImpl(HoldingDao holdingDao) {
        this.holdingDao = holdingDao;
    }

    @Override
    public void buyHolding(UUID userId, String symbol, BigDecimal amount) {
        Holding holding = holdingDao.findByUserIdAndSymbol(userId, symbol);

        if(holding == null) {
            createHolding(userId, symbol, amount);

            return;
        }

        BigDecimal newAmount = holding.getAmount().add(amount);

        holding.setAmount(newAmount);

        holdingDao.update(holding);
    }

    @Override
    public void sellHolding(UUID userId, String symbol, BigDecimal amount) {
        Holding holding = holdingDao.findByUserIdAndSymbol(userId, symbol);

        if(holding == null) {
            return;
        }

        if(holding.getAmount().compareTo(amount) < 0) {
            return;
        }

        BigDecimal newAmount = holding.getAmount().subtract(amount);

        if(newAmount.compareTo(BigDecimal.ZERO) == 0) {
            holdingDao.delete(holding.getId());
            return;
        }

        holding.setAmount(newAmount);

        holding.setAmount(newAmount);

        holdingDao.update(holding);
    }

    @Override
    public Holding getByUserIdAndSmybol(UUID userId, String symbol) {
        return holdingDao.findByUserIdAndSymbol(userId, symbol);
    }

    @Override
    public void deleteUserHoldings(UUID userId)
    {
        holdingDao.deleteUserHoldings(userId);
    }

    @Override
    public void createHolding(UUID userId, String symbol, BigDecimal amount) {
        Holding holding = Holding.builder()
                .userId(userId)
                .symbol(symbol)
                .amount(amount)
                .build();

        holdingDao.create(holding);
    }
}
