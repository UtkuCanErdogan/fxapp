package com.utkucan.fxapp.application.service;

import com.utkucan.fxapp.application.dto.request.CsvUploadRequest;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.BulkCsvResponse;
import com.utkucan.fxapp.application.dto.response.ExchangeCsvResponse;
import com.utkucan.fxapp.application.dto.response.ExchangeResponse;
import com.utkucan.fxapp.common.ContainerTestBase;
import com.utkucan.fxapp.common.exception.ExceptionCode;
import com.utkucan.fxapp.common.exception.FileReadException;
import com.utkucan.fxapp.common.exception.InvalidCurrencyException;
import com.utkucan.fxapp.domain.entity.Currency;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import com.utkucan.fxapp.domain.repository.CurrencyRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowableOfType;


@SpringBootTest
@ActiveProfiles("test")
class ExchangeServiceTest extends ContainerTestBase {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private CurrencyRepository currencyRepository;

    @BeforeEach
    void setupRedisCurrencies() {
        long timestamp = System.currentTimeMillis();

        Set<Currency> currencies = Set.of(
                new Currency("USD", BigDecimal.valueOf(1.0), CurrencyCode.USD, timestamp),
                new Currency("TRY", BigDecimal.valueOf(0.031), CurrencyCode.USD, timestamp),
                new Currency("EUR", BigDecimal.valueOf(1.10), CurrencyCode.USD, timestamp),
                new Currency("GBP", BigDecimal.valueOf(1.25), CurrencyCode.USD, timestamp),
                new Currency("JPY", BigDecimal.valueOf(0.0061), CurrencyCode.USD, timestamp)
        );

        currencyRepository.saveAll(currencies);
    }

    @Test
    void shouldConvertFromTryToEurSuccessfully() {
        CurrencyCode from = CurrencyCode.TRY;
        CurrencyCode to = CurrencyCode.EUR;
        Long amount = 10000L;

        ExchangeRequest request = new ExchangeRequest(from, to, amount);
        ExchangeResponse response = exchangeService.convertCurrencies(request);

        BigDecimal expectedRate = BigDecimal.valueOf(1.10).divide(BigDecimal.valueOf(0.031), 8, RoundingMode.HALF_UP);
        BigDecimal rawResult = BigDecimal.valueOf(request.getAmount()).multiply(expectedRate);
        long expectedAmount = rawResult.setScale(0, RoundingMode.HALF_UP).longValue();

        assertThat(response.getRate()).isEqualByComparingTo(expectedRate);
        assertThat(response.getAmount()).isEqualByComparingTo(expectedAmount);
    }

    @Test
    void shouldThrowExceptionWhenFromAndToCurrenciesAreSame() {
        CurrencyCode from = CurrencyCode.EUR;
        CurrencyCode to = CurrencyCode.EUR;
        Long amount = 10000L;

        ExchangeRequest request = new ExchangeRequest(from, to, amount);
        assertThatThrownBy(() -> exchangeService.convert(request))
                .isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("Source and target currencies must be different");
    }

    @Test
    void shouldReturnThreeValidationErrors_WhenAllFieldsAreNull() {
        ExchangeRequest request = new ExchangeRequest(null ,null ,null);
        ConstraintViolationException ex = catchThrowableOfType(() ->
                        exchangeService.convert(request),
                ConstraintViolationException.class
        );

        assertThat(ex.getConstraintViolations()).hasSize(3);
        assertThat(ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage))
                .containsExactlyInAnyOrder(
                        "Source currency is required",
                        "Target currency is required",
                        "Amount is required"
                );
    }

    @Test
    void shouldFailValidation_WhenAmountIsOutOfRange() {
        CurrencyCode from = CurrencyCode.EUR;
        CurrencyCode to = CurrencyCode.USD;
        Long minAmount = 50L;
        Long maxAmount = 100000000000L;

        ExchangeRequest minRequest = new ExchangeRequest(from , to, minAmount);
        ExchangeRequest maxRequest = new ExchangeRequest(from , to, maxAmount);

        ConstraintViolationException minEx = catchThrowableOfType(() ->
                        exchangeService.convert(minRequest),
                ConstraintViolationException.class
        );

        assertThat(minEx.getConstraintViolations()).hasSize(1);
        assertThat(minEx.getConstraintViolations().iterator().next().getMessage())
                .isEqualTo("Amount must be at least 100");

        ConstraintViolationException maxEx = catchThrowableOfType(() ->
                        exchangeService.convert(maxRequest),
                ConstraintViolationException.class
        );

        assertThat(maxEx.getConstraintViolations()).hasSize(1);
        assertThat(maxEx.getConstraintViolations().iterator().next().getMessage())
                .isEqualTo("Amount must not exceed 10000000000");
    }

    @Test
    void shouldProcessAllExchangesSuccessfully_WhenValidCsvFilesProvided() throws Exception {
        Map<String, List<String[]>> fileInputMap = new HashMap<>();
        ClassPathResource valid1 = new ClassPathResource("test-files/success_bulk1.csv");
        ClassPathResource valid2 = new ClassPathResource("test-files/success_bulk2.csv");

        MockMultipartFile file1 = new MockMultipartFile("files", "success_bulk1.csv", "text/csv", valid1.getInputStream());
        MockMultipartFile file2 = new MockMultipartFile("files", "success_bulk2.csv", "text/csv", valid2.getInputStream());

        MultipartFile[] files = new MultipartFile[]{file1, file2};

        for (MultipartFile file : files) {
            List<String[]> csvLines;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                csvLines = reader.lines()
                        .map(line -> line.split(","))
                        .collect(Collectors.toList());
            }

            fileInputMap.put(file.getOriginalFilename(), csvLines);
        }

        List<BulkCsvResponse> responses = exchangeService.processBulkCsv(new CsvUploadRequest(new MultipartFile[]{file1, file2}));

        assertThat(responses).hasSize(2);

        for (BulkCsvResponse bulkResponse : responses) {
            String fileName = bulkResponse.getFileName();
            List<ExchangeCsvResponse> resultList = bulkResponse.getExchangeResults();
            List<String[]> csvLines = fileInputMap.get(fileName);

            assertThat(resultList).hasSize(csvLines.size());

            for (int i = 0; i < resultList.size(); i++) {
                ExchangeCsvResponse res = resultList.get(i);
                String[] csv = csvLines.get(i);

                String from = csv[0];
                String to = csv[1];
                long amount = Long.parseLong(csv[2].trim());

                assertThat(res.getFrom()).isEqualTo(from);
                assertThat(res.getTarget()).isEqualTo(to);
                assertThat(res.getRate()).isGreaterThan(BigDecimal.ZERO);

                BigDecimal rawResult = BigDecimal.valueOf(amount).multiply(res.getRate());
                long expectedAmount = rawResult.setScale(0, RoundingMode.HALF_UP).longValue();
                assertThat(res.getAmount()).isEqualByComparingTo(expectedAmount);
            }
        }
    }

    @Test
    void shouldFail_WhenCsvFileContainsSameCurrencyPairs() throws Exception {
        ClassPathResource invalidFile = new ClassPathResource("test-files/failed_bulk.csv");
        MockMultipartFile file = new MockMultipartFile("files", "failed_bulk.csv", "text/csv", invalidFile.getInputStream());

        ConstraintViolationException ex = catchThrowableOfType(() ->
                        exchangeService.processBulkCsv(new CsvUploadRequest(new MultipartFile[]{file})),
                ConstraintViolationException.class
        );

        assertThat(ex.getConstraintViolations())
                .anySatisfy(v -> assertThat(v.getMessage()).contains("Source and target currencies must be different"));
    }

    @Test
    void shouldThrowException_WhenNonCsvFileUploaded() throws Exception {
        ClassPathResource invalidTxt = new ClassPathResource("test-files/invalid_file.txt");
        MockMultipartFile file = new MockMultipartFile(
                "files",
                "fake_broken.txt",
                "text/plain",
                invalidTxt.getInputStream()
        );

        FileReadException ex = catchThrowableOfType(() ->
                        exchangeService.processBulkCsv(new CsvUploadRequest(new MultipartFile[]{file})),
                FileReadException.class
        );

        assertThat(ex)
                .isInstanceOf(FileReadException.class)
                .hasMessageContaining(ExceptionCode.FILE_READ_EXCEPTION.message());
    }

    @Test
    void shouldThrowException_WhenUnSupportedCurrencyGiven() throws Exception {
        ClassPathResource invalidTxt = new ClassPathResource("test-files/invalid_currency.csv");
        MockMultipartFile file = new MockMultipartFile(
                "files",
                "invalid_currency.csv",
                "text/csv",
                invalidTxt.getInputStream()
        );

        InvalidCurrencyException ex = catchThrowableOfType(() ->
                        exchangeService.processBulkCsv(new CsvUploadRequest(new MultipartFile[]{file})),
                InvalidCurrencyException.class
        );

        assertThat(ex)
                .isInstanceOf(InvalidCurrencyException.class)
                .hasMessageContaining(ExceptionCode.INVALID_CURRENCY_EXCEPTION.message());
    }
}