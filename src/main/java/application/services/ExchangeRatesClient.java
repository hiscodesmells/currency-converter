package application.services;

import application.domain.SupportedSymbolsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "${exchangeRates.name}", url = "${exchangeRates.url}")
public interface ExchangeRatesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/symbols", consumes = "application/json")
    SupportedSymbolsResponse getSymbols(@RequestParam("access_key") String apiKey);

}
