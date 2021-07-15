package org.nosto.assignment.currencyconverter.services;

import java.util.Set;

public interface ExchangeRatesService {
    /**
     * Get list of all supported symbols
     *
     * @return List of supported symbols
     */
    Set<String> getSymbols();

    /**
     * Get latest rate of conversion
     *
     * @param source - Currency symbol to be converted from
     * @param target - Currency symbol to be converted to
     * @return
     */
    Float getRate(String source, String target);
}
