package org.nosto.assignment.currencyconverter.services;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nosto.assignment.currencyconverter.exceptions.RateUnavailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

import static org.nosto.assignment.currencyconverter.constants.Constants.MAX_FRACTIONAL_DIGITS;

@Service
@Setter
@Slf4j
public class ConversionServiceImpl implements ConversionService {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Override
    public String convert(String source, String target, String amount) {
        Double rate = exchangeRatesService.getRate(source, target);
        if (rate == null) {
            log.error("Failed to get latest rate source={} target={} amount={}", source, target, amount);
            throw new RateUnavailableException("Failed to get latest rate");
        }
        Double convertedAmount = Double.parseDouble(amount) * rate;
        return localizeAmount(convertedAmount, target);
    }

    private String localizeAmount(Double amount, String currency) {
        for (Locale locale: NumberFormat.getAvailableLocales()) {

            NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
            numberFormat.setMaximumFractionDigits(MAX_FRACTIONAL_DIGITS);
            if (numberFormat.getCurrency().getCurrencyCode().equalsIgnoreCase(currency)) {
                return numberFormat.format(amount);
            }
        }
        return String.valueOf(amount);
    }
}
