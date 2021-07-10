package application.services;

import application.domain.SupportedSymbolsResponse;
import feign.FeignException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Set;

import static application.constants.Constants.SYMBOLS_CACHE;

@Service
@Setter
@Slf4j
public class ExchangeRatesServiceImpl implements ExchangeRatesService {

    @Value("${exchangeRates.apiKey}")
    private String apiKey;

    @Autowired
    private ExchangeRatesClient exchangeRatesClient;

    @Override
    @Cacheable(SYMBOLS_CACHE)
    public Set<String> getSymbols() {
        Set<String> symbols = null;
        try {
            SupportedSymbolsResponse response = exchangeRatesClient.getSymbols(apiKey);
            if (response.isSuccess()) {
                symbols = response.getSymbols().keySet();
            }
            log.info("Successfully fetched supported symbols symbols={}", symbols);
        } catch (FeignException e) {
            log.error("Failed to get symbols code={} error={}", e.status(), e.getMessage());
        } catch (Exception e) {
            log.error("Failed to get symbols error={}", e.getMessage());
        }
        return symbols;
    }
}
