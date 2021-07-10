package application.controllers;

import application.domain.ConvertResponse;
import application.services.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static application.constants.APIConstants.CONVERT_PATH;

@Slf4j
@RestController
public class ConvertController {

    @Autowired
    private ValidationService validationService;

    @RequestMapping(method = RequestMethod.GET, value = CONVERT_PATH)
    public ConvertResponse convert(@RequestParam("source") String source, @RequestParam("target") String target,
                                   @RequestParam("value") String value) {
        log.info("Received convert request source={} target={} value={}", source, target, value);
        validationService.validateConvertRequest(source, target, value);
        log.info("Successfully validated convert request");

        return new ConvertResponse(0.0);
    }
}
