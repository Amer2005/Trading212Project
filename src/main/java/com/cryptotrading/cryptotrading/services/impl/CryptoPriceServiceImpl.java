package com.cryptotrading.cryptotrading.services.impl;

import com.cryptotrading.cryptotrading.services.CryptoPriceService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.json.JSONObject;

import java.math.BigDecimal;

@Component
public class CryptoPriceServiceImpl implements CryptoPriceService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String KRAKEN_API_URL = "https://api.kraken.com/0/public/Ticker";

    @Override
    public BigDecimal getPrice(String symbol) {
        String krakenPair = mapToKrakenPair(symbol);

        String url = UriComponentsBuilder
                .fromHttpUrl(KRAKEN_API_URL)
                .queryParam("pair", krakenPair)
                .toUriString();

        try {
            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = new JSONObject(response);

            JSONObject result = json.getJSONObject("result");
            JSONObject pairData = result.getJSONObject(result.keys().next());

            // "c" is the last trade closed array: [<price>, <lot volume>]
            String priceStr = pairData.getJSONArray("c").getString(0);

            return new BigDecimal(priceStr);
        } catch (Exception e) {
            return BigDecimal.valueOf(-1); // or throw a custom exception
        }
    }

    private String mapToKrakenPair(String symbol) {
        return symbol + "USD";
    }
}
