package application.services;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Setter
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    public boolean isValidSymbol(String symbol) {
        if (symbol == null || symbol.isEmpty()) {
            return false;
        }
        symbol = symbol.trim().toUpperCase();
        return exchangeRatesService.getSymbols().contains(symbol);
    }

    public boolean isValidValue(String value) {
        if (value == null || value.isEmpty()) {
            return false;
        }
        value = value.trim();
        try {
            Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public void validateConvertRequest(String source, String target, String value) {
        if (!isValidSymbol(source)) {
            log.error("Invalid source currency symbol source={}", source);
            throw new IllegalArgumentException("Invalid source currency symbol");
        }
        if (!isValidSymbol(target)) {
            log.error("Invalid target currency symbol target={}", target);
            throw new IllegalArgumentException("Invalid target currency symbol");
        }
        if (source.toUpperCase().trim().equals(target.toUpperCase().trim())) {
            log.error("Invalid input, source and target can not be same");
            throw new IllegalArgumentException("Invalid input, source and target can not be same");
        }
        if (!isValidValue(value)) {
            log.error("Invalid value provided value={}", value);
            throw new IllegalArgumentException("Invalid value provided");
        }
        log.info("Successfully validated convert request source={} target={}", source, target);
    }
}
