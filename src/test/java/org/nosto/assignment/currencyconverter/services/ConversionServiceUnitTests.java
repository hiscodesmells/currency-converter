package org.nosto.assignment.currencyconverter.services;

import org.nosto.assignment.currencyconverter.extensions.RateUnavailableExceptionExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ConversionServiceUnitTests {

    private final ConversionServiceImpl conversionService = new ConversionServiceImpl();

    @Mock
    private ExchangeRatesService exchangeRatesService;

    @BeforeEach
    public void setup() {
        conversionService.setExchangeRatesService(exchangeRatesService);
    }

    @Test
    public void test_convert() {
        String source = "USD";
        String target = "EUR";
        String amount = "10";
        when(exchangeRatesService.getRate(source, target)).thenReturn(10f);
        Float result = conversionService.convert(source, target, amount);
        verify(exchangeRatesService).getRate(source, target);
        Assertions.assertEquals(result, 100f);
    }

    @Test
    @ExtendWith(RateUnavailableExceptionExtension.class)
    public void test_convertWithNullRate() {
        String source = "USD";
        String target = "EUR";
        String amount = "10";
        when(exchangeRatesService.getRate(source, target)).thenReturn(null);
        conversionService.convert(source, target, amount);
        verify(exchangeRatesService).getRate(source, target);
    }

}
