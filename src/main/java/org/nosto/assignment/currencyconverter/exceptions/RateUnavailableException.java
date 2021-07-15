package org.nosto.assignment.currencyconverter.exceptions;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RateUnavailableException extends RuntimeException {
    private String message;

    public RateUnavailableException(String message) {
        super(message);
    }
}
