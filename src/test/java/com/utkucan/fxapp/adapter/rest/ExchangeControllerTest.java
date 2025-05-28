package com.utkucan.fxapp.adapter.rest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldConvertSuccessfully() throws Exception {
        String requestJson = """
            {
              "from": "USD",
              "to": "TRY",
              "amount": 100.0
            }
        """;

        mockMvc.perform(post("/api/exchange/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.amount").exists())
                .andExpect(jsonPath("$.data.rate").exists());
    }

    @Test
    void shouldFailWhenFromAndToAreSame() throws Exception {
        String requestJson = """
        {
          "from": "USD",
          "to": "USD",
          "amount": 100
        }
    """;

        mockMvc.perform(post("/api/exchange/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    @Test
    void shouldFailWhenFromCurrencyIsMissing() throws Exception {
        String invalidRequest = """
        {
          "to": "TRY",
          "amount": 100
        }
    """;

        mockMvc.perform(post("/api/exchange/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.validationErrors").isArray())
                .andExpect(jsonPath("$.validationErrors[0].field").value("from"))
                .andExpect(jsonPath("$.validationErrors[0].message").value("Source currency is required"));
    }

    @Test
    void shouldConvertCsvSuccessfully() throws Exception {
        ClassPathResource valid1 = new ClassPathResource("test-files/success_bulk1.csv");
        ClassPathResource valid2 = new ClassPathResource("test-files/success_bulk2.csv");

        MockMultipartFile file1 = new MockMultipartFile("file", "success_bulk1.csv", "text/csv", valid1.getInputStream());
        MockMultipartFile file2 = new MockMultipartFile("file", "success_bulk2.csv", "text/csv", valid2.getInputStream());

        mockMvc.perform(multipart("/api/exchange/convert-csv")
                        .file(file1)
                        .file(file2)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    void shouldFailWhenCsvIsMissing() throws Exception {
        mockMvc.perform(multipart("/api/exchange/convert-csv"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void shouldReturnExchangeHistorySuccessfully() throws Exception {
        mockMvc.perform(get("/api/exchange/history")
                        .param("skip", "0")
                        .param("limit", "10")
                        .param("sortBy", "ASC")
                        .param("orderBy", "createdAt")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray());
    }

}