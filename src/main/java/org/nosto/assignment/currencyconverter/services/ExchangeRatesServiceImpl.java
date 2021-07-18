package org.nosto.assignment.currencyconverter.services;

import feign.FeignException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nosto.assignment.currencyconverter.domain.RateResponse;
import org.nosto.assignment.currencyconverter.domain.SupportedSymbolsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import static org.nosto.assignment.currencyconverter.constants.Constants.RATES_CACHE;
import static org.nosto.assignment.currencyconverter.constants.Constants.SYMBOLS_CACHE;

@Service
@Setter
@Slf4j
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    @Value("${exchangeRates.apiKey}")
    private String apiKey;

    @Autowired
    private ExchangeRatesClient exchangeRatesClient;

    @Override
    @Cacheable(value = SYMBOLS_CACHE, unless = "#result == null")
    public Set<String> getSymbols() {
        Set<String> symbols = null;
        try {
            SupportedSymbolsResponse response = exchangeRatesClient.getSymbols(apiKey);
                if (response.isSuccess()) {
                    symbols = response.getSymbols().keySet();
                    log.info("Successfully fetched supported symbols symbols={}", symbols);
                }
        } catch (FeignException e) {
            log.error("Failed to get symbols code={} error={}", e.status(), e.getMessage());
        } catch (Exception e) {
            log.error("Failed to get symbols error={}", e.getMessage(), e);
        }
        return symbols;
    }

    @Override
    @Cacheable(value = RATES_CACHE, unless = "#result == null")
    public Double getRate(String source, String target) {
        Double rate = null;
        try {
            RateResponse response = exchangeRatesClient.getRate(apiKey, source, Collections.singleton(target));
            if (response.isSuccess()) {
                rate = Double.parseDouble(new ArrayList<>(response.getRates().values()).get(0));
            }
            log.info("Successfully fetched latest rate source={} target={}", source, target);
        } catch (FeignException e) {
            log.error("Failed to convert currency code={} error={}", e.status(), e.getMessage());
        } catch (Exception e) {
            log.error("Failed to convert currency error={}", e.getMessage(), e);
        }
        return rate;
    }
}
