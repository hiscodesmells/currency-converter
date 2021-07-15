package org.nosto.assignment.currencyconverter.services;

import org.nosto.assignment.currencyconverter.exceptions.RateUnavailableException;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Setter
@Slf4j
public class ConversionServiceImpl implements ConversionService {

    @Autowired
    private ExchangeRatesService exchangeRatesService;

    @Override
    public Float convert(String source, String target, String amount) {
        Float rate = exchangeRatesService.getRate(source, target);
        if (rate == null) {
            log.error("Failed to get latest rate source={} target={} amount={}", source, target, amount);
            throw new RateUnavailableException("Failed to get latest rate");
        }
        return Float.parseFloat(amount) * rate;
    }
}
