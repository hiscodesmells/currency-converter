package application.services;

import java.util.Set;

public interface ExchangeRatesService {
    /**
     * Get list of all supported symbols
     *
     * @return List of supported symbols
     */
    Set<String> getSymbols();
}
