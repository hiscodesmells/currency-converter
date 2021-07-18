package org.nosto.assignment.currencyconverter.services;

public interface ConversionService {
    /**
     * Convert the given amount of currency from source type to target type
     *
     * @param source - Currency symbol to be converted from
     * @param target - Currency symbol to be converted to
     * @param amount - Amount of currency to be converted
     * @return - Amount in target currency
     */
    String convert(String source, String target, String amount);
}
