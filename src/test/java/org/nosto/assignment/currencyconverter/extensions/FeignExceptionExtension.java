package org.nosto.assignment.currencyconverter.extensions;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class FeignExceptionExtension implements TestExecutionExceptionHandler {

    @Override
    public void handleTestExecutionException(ExtensionContext extensionContext, Throwable throwable) throws Throwable {

        if (throwable instanceof IllegalArgumentException) {
            return;
        }
        throw throwable;
    }
}
