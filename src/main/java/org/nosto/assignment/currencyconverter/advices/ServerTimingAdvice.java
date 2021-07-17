package org.nosto.assignment.currencyconverter.advices;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import static org.nosto.assignment.currencyconverter.constants.APIConstants.SERVER_TIMING_HEADER;

@ControllerAdvice
@Slf4j
public class ServerTimingAdvice implements ResponseBodyAdvice<Object> {

    private static final String START_TIME = "startTime";

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) serverHttpRequest;
        Long startTime = (Long) servletServerHttpRequest.getServletRequest().getAttribute(START_TIME);
        if (startTime != null) {
            Long endTime = System.currentTimeMillis();
            String serverTimingHeader = "total;dur=" + (endTime - startTime);
            serverHttpResponse.getHeaders().add(SERVER_TIMING_HEADER, serverTimingHeader);
            log.info("Added {} header to response header={}", SERVER_TIMING_HEADER, serverTimingHeader);
        }
        return body;
    }
}
