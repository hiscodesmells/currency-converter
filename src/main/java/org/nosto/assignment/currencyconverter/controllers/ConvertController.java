package org.nosto.assignment.currencyconverter.controllers;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.nosto.assignment.currencyconverter.services.ConversionService;
import org.nosto.assignment.currencyconverter.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.nosto.assignment.currencyconverter.constants.APIConstants.CONVERT_PATH;

@Slf4j
@Setter
@RestController
public class ConvertController {

    @Autowired
    private ValidationService validationService;

    @Autowired
    private ConversionService conversionService;

    @RequestMapping(method = RequestMethod.GET, value = CONVERT_PATH)
    public String convert(@RequestParam("source") String source, @RequestParam("target") String target,
                                  @RequestParam("amount") String amount) {
        log.info("Received convert request source={} target={} value={}", source, target, amount);
        validationService.validateConvertRequest(source, target, amount);
        log.info("Successfully validated convert request");
        String result = conversionService.convert(source, target, amount);
        log.info("Successfully converted currency source={} target={} amount={} result={}", source, target, amount, result);
        return result;
    }
}
