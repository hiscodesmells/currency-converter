package org.nosto.assignment.currencyconverter.services;

public interface ValidationService {
    /**
     * Validate the convert request
     *
     * @param source - Currency symbol to be converted from
     * @param target - Currency symbol to be converted to
     * @param amount - Amount of currency to be converted
     */
    void validateConvertRequest(String source, String target, String amount);
}
