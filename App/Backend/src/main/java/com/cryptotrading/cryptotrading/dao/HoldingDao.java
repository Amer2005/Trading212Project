package com.cryptotrading.cryptotrading.dao;

import com.cryptotrading.cryptotrading.domain.Holding;

import java.util.UUID;

public interface HoldingDao {
    void create(Holding holding);

    Holding findOne(UUID id);

    void update(Holding holding);

    Holding findByUserIdAndSymbol(UUID userId, String symbol);

    void delete(UUID id);
}
