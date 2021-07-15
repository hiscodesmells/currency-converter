package org.nosto.assignment.currencyconverter.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class APIConstants {
    public static final String BASE_API_PATH = "/api";
    public static final String CONVERT_PATH = BASE_API_PATH + "/convert";
    public static final String SERVER_TIMING_HEADER = "Server-Timing";
}
