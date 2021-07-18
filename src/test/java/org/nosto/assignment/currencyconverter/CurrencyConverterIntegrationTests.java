package org.nosto.assignment.currencyconverter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.nosto.assignment.currencyconverter.constants.APIConstants.CONTENT_SECURITY_POLICY_HEADER;
import static org.nosto.assignment.currencyconverter.constants.APIConstants.SERVER_TIMING_HEADER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CurrencyConverterApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class CurrencyConverterIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    private static final String SOURCE_PARAM = "source";
    private static final String TARGET_PARAM = "target";
    private static final String AMOUNT_PARAM = "amount";

    @Test
    public void test_convert() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/convert")
                            .param(SOURCE_PARAM, "EUR")
                            .param(TARGET_PARAM, "USD")
                            .param(AMOUNT_PARAM, "10.0"))
                .andDo(print()).andReturn();
        Assertions.assertEquals(result.getResponse().getContentAsString(), "$100.00");
        Assertions.assertNotNull(result.getResponse().getHeader(SERVER_TIMING_HEADER));
        Assertions.assertEquals(result.getResponse().getHeader(CONTENT_SECURITY_POLICY_HEADER), "script-src 'self'");
    }

    @Test
    public void test_convertWithSameSourceAndTargetSymbols() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/convert")
                .param(SOURCE_PARAM, "EUR")
                .param(TARGET_PARAM, "EUR")
                .param(AMOUNT_PARAM, "10.0"))
                .andDo(print()).andExpect(status().isBadRequest()).andReturn();
        Assertions.assertNotNull(result.getResponse().getHeader(SERVER_TIMING_HEADER));
    }

    @Test
    public void test_convertWhenSourceNotPresent() throws Exception {
        mockMvc.perform(get("/api/convert")
            .param(TARGET_PARAM, "EUR")
            .param(AMOUNT_PARAM, "100")
            .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_convertWhenTargetNotPresent() throws Exception {
        mockMvc.perform(get("/api/convert")
                .param(SOURCE_PARAM, "USD")
                .param(AMOUNT_PARAM, "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_convertWhenAmountNotPresent() throws Exception {
        mockMvc.perform(get("/api/convert")
                .param(SOURCE_PARAM, "USD")
                .param(TARGET_PARAM, "EUR")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_convertWithEmptySource() throws Exception {
        mockMvc.perform(get("/api/convert")
                .param(SOURCE_PARAM, "")
                .param(TARGET_PARAM, "EUR")
                .param(AMOUNT_PARAM, "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_convertWithEmptyTarget() throws Exception {
        mockMvc.perform(get("/api/convert")
                .param(SOURCE_PARAM, "USD")
                .param(TARGET_PARAM, "")
                .param(AMOUNT_PARAM, "100")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void test_convertWithEmptyAmount() throws Exception {
        mockMvc.perform(get("/api/convert")
                .param(SOURCE_PARAM, "USD")
                .param(TARGET_PARAM, "EUR")
                .param(AMOUNT_PARAM, "")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
