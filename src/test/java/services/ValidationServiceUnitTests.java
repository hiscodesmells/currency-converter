package services;

import application.services.ExchangeRatesService;
import application.services.ValidationServiceImpl;
import extensions.IllegalArgumentExceptionExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ValidationServiceUnitTests {

    private final ValidationServiceImpl validationService = new ValidationServiceImpl();

    @Mock
    private ExchangeRatesService exchangeRatesService;

    @BeforeEach
    public void setup() {
        validationService.setExchangeRatesService(exchangeRatesService);
    }

    @Test
    public void test_isValidSymbolWithEmptySymbol() {
        Assertions.assertFalse(validationService.isValidSymbol(""));
    }

    @Test
    public void test_isValidSymbolWithNullSymbol() {
        Assertions.assertFalse(validationService.isValidSymbol(null));
    }

    @Test
    public void test_isValidSymbolWithInValidSymbol() {
        when(exchangeRatesService.getSymbols()).thenReturn(Collections.singleton("QWERTY"));
        boolean isValidSymbol = validationService.isValidSymbol("   abcd  ");
        verify(exchangeRatesService).getSymbols();
        Assertions.assertFalse(isValidSymbol);
    }

    @Test
    public void test_isValidSymbolWithValidSymbol() {
        when(exchangeRatesService.getSymbols()).thenReturn(Collections.singleton("ABCD"));
        boolean isValidSymbol = validationService.isValidSymbol("   abcd  ");
        verify(exchangeRatesService).getSymbols();
        Assertions.assertTrue(isValidSymbol);
    }

    @Test
    public void test_isValidValueWithEmptyValue() {
        Assertions.assertFalse(validationService.isValidValue(""));
    }

    @Test
    public void test_isValidValueWithNullValue() {
        Assertions.assertFalse(validationService.isValidValue(null));
    }

    @Test
    public void test_isValidValueWithInvalidValue() {
        Assertions.assertFalse(validationService.isValidValue("abcd"));
    }

    @Test
    public void test_isValidValueWithValidValue() {
        Assertions.assertTrue(validationService.isValidValue(" 123.456 "));
    }

    @Test
    @ExtendWith(IllegalArgumentExceptionExtension.class)
    public void test_validateConvertRequestWithInvalidSourceSymbol() {
        when(exchangeRatesService.getSymbols()).thenReturn(Collections.singleton("QWERTY"));
        validationService.validateConvertRequest("abcd", "qwerty", "123.456");
        verify(exchangeRatesService).getSymbols();
    }

    @Test
    @ExtendWith(IllegalArgumentExceptionExtension.class)
    public void test_validateConvertRequestWithInvalidTargetSymbol() {
        when(exchangeRatesService.getSymbols()).thenReturn(Collections.singleton("ABCD"));
        validationService.validateConvertRequest("abcd", "qwerty", "123.456");
        verify(exchangeRatesService).getSymbols();
    }

    @Test
    @ExtendWith(IllegalArgumentExceptionExtension.class)
    public void test_validateConvertRequestWithInvalidValue() {
        when(exchangeRatesService.getSymbols()).thenReturn(new HashSet<>(Arrays.asList("ABCD", "QWERTY")));
        validationService.validateConvertRequest("abcd", "qwerty", "123.456.789");
        verify(exchangeRatesService).getSymbols();
    }

    @Test
    @ExtendWith(IllegalArgumentExceptionExtension.class)
    public void test_validateConvertRequestWithSameSourceAndTarget() {
        when(exchangeRatesService.getSymbols()).thenReturn(Collections.singleton("ABCD"));
        validationService.validateConvertRequest("abcd", "abcd", "123.456");
        verify(exchangeRatesService).getSymbols();
    }

    @Test
    public void test_validateConvertRequestWithValidSymbol() {
        when(exchangeRatesService.getSymbols()).thenReturn(new HashSet<>(Arrays.asList("ABCD", "QWERTY")));
        validationService.validateConvertRequest("abcd", "qwerty", "123.456");
        verify(exchangeRatesService, times(2)).getSymbols();
    }
}
