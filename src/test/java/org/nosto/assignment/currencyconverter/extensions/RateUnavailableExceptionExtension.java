package org.nosto.assignment.currencyconverter.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.nosto.assignment.currencyconverter.exceptions.RateUnavailableException;

public class RateUnavailableExceptionExtension implements TestExecutionExceptionHandler {
    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {

        if (throwable instanceof RateUnavailableException) {
            return;
        }
        throw throwable;
    }
}
