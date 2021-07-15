package org.nosto.assignment.currencyconverter.services;

import org.nosto.assignment.currencyconverter.domain.RateResponse;
import org.nosto.assignment.currencyconverter.domain.SupportedSymbolsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Set;

@FeignClient(value = "${exchangeRates.name}", url = "${exchangeRates.url}")
public interface ExchangeRatesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/symbols", consumes = "application/json")
    SupportedSymbolsResponse getSymbols(@RequestParam("access_key") String apiKey);

    @RequestMapping(method = RequestMethod.GET, value = "/latest", consumes = "application/json")
    RateResponse getRate(@RequestParam("access_key") String apiKey, @RequestParam("base") String base,
                         @RequestParam("symbols") Set<String> symbols);

}
