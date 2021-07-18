package org.nosto.assignment.currencyconverter.services;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nosto.assignment.currencyconverter.exceptions.RateUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@Setter
@Slf4j
public class ConversionServiceImpl implements ConversionService {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Override
    public String convert(String source, String target, String amount) {
        Float rate = exchangeRatesService.getRate(source, target);
        if (rate == null) {
            log.error("Failed to get latest rate source={} target={} amount={}", source, target, amount);
            throw new RateUnavailableException("Failed to get latest rate");
        }
        Float convertedAmount = Float.parseFloat(amount) * rate;
        return localizeAmount(convertedAmount, target);
    }

    private String localizeAmount(Float amount, String currency) {
        for (Locale locale: NumberFormat.getAvailableLocales()) {
            if (NumberFormat.getCurrencyInstance(locale).getCurrency().getCurrencyCode().equalsIgnoreCase(currency)) {
                return NumberFormat.getCurrencyInstance(locale).format(amount);
            }
        }
        return String.valueOf(amount);
    }
}
