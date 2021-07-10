package services;

import application.domain.SupportedSymbolsResponse;
import application.services.ExchangeRatesClient;
import application.services.ExchangeRatesServiceImpl;
import extensions.FeignExceptionExtension;
import extensions.NullPointerExceptionExtension;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceUnitTests {

    private final ExchangeRatesServiceImpl exchangeRatesService = new ExchangeRatesServiceImpl();

    private final String API_KEY = "API-KEY";

    @Mock
    private ExchangeRatesClient exchangeRatesClient;

    @BeforeEach
    public void setup() {
        exchangeRatesService.setExchangeRatesClient(exchangeRatesClient);
        exchangeRatesService.setApiKey(API_KEY);
    }

    @Test
    public void test_getSymbolsSuccess() {
        Map<String, String> symbols = Collections.singletonMap("USD", "United States Dollar");
        when(exchangeRatesClient.getSymbols(API_KEY)).thenReturn(new SupportedSymbolsResponse(true, symbols));
        Set<String> supportedSymbols = exchangeRatesService.getSymbols();
        verify(exchangeRatesClient).getSymbols(API_KEY);
        Assertions.assertEquals(supportedSymbols, Collections.singleton("USD"));
    }

    @Test
    public void test_getSymbolsFailure() {
        when(exchangeRatesClient.getSymbols(API_KEY)).thenReturn(new SupportedSymbolsResponse(false, null));
        Set<String> supportedSymbols = exchangeRatesService.getSymbols();
        verify(exchangeRatesClient).getSymbols(API_KEY);
        Assertions.assertNull(supportedSymbols);
    }

    @Test
    @ExtendWith(FeignExceptionExtension.class)
    public void test_getSymbolsFeignException() {
        when(exchangeRatesClient.getSymbols(API_KEY)).thenThrow(new FeignException.BadRequest("Bad Request",
                Request.create(Request.HttpMethod.GET, "", Collections.EMPTY_MAP, Request.Body.create(""), null),
                null));
        exchangeRatesService.getSymbols();
        verify(exchangeRatesClient).getSymbols(API_KEY);
    }

    @Test
    @ExtendWith(NullPointerExceptionExtension.class)
    public void test_getSymbolsException() {
        when(exchangeRatesClient.getSymbols(API_KEY)).thenThrow(new NullPointerException());
        exchangeRatesService.getSymbols();
        verify(exchangeRatesClient).getSymbols(API_KEY);
    }
}
