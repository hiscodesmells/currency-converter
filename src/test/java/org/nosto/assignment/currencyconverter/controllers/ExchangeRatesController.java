package org.nosto.assignment.currencyconverter.controllers;

import org.nosto.assignment.currencyconverter.domain.RateResponse;
import org.nosto.assignment.currencyconverter.domain.SupportedSymbolsResponse;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/test")
@Profile("test")
public class ExchangeRatesController {

    @RequestMapping(method = RequestMethod.GET, value = "/symbols", consumes = "application/json")
    SupportedSymbolsResponse getSymbols(@RequestParam("access_key") String apiKey) {
        return new SupportedSymbolsResponse(true, new HashMap<String, String>(){{
            put("USD", "United States Dollars");
            put("EUR", "Euro");
        }});
    }

    @RequestMapping(method = RequestMethod.GET, value = "/latest", consumes = "application/json")
    RateResponse getRate(@RequestParam("access_key") String apiKey, @RequestParam("base") String base,
                         @RequestParam("symbols") Set<String> symbols) {
        return new RateResponse(true, new HashMap<String, String>(){{
            put("EUR", "10.0");
        }});
    }

}
