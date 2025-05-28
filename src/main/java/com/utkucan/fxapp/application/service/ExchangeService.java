package com.utkucan.fxapp.application.service;

import com.utkucan.fxapp.application.dto.request.ExchangeHistorySaveRequest;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.BulkCsvResponse;
import com.utkucan.fxapp.application.dto.response.ExchangeCsvResponse;
import com.utkucan.fxapp.application.dto.response.ExchangeResponse;
import com.utkucan.fxapp.common.utils.ValidationUtil;
import com.utkucan.fxapp.domain.entity.Currency;
import com.utkucan.fxapp.domain.repository.CurrencyRepository;
import com.utkucan.fxapp.common.utils.CsvUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
public class ExchangeService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeHistoryService exchangeHistoryService;

    public ExchangeService(CurrencyRepository currencyRepository, ExchangeHistoryService exchangeHistoryService) {
        this.currencyRepository = currencyRepository;
        this.exchangeHistoryService = exchangeHistoryService;
    }

    public ExchangeResponse convertCurrencies(ExchangeRequest exchangeRequest) {
        ExchangeResponse response = convert(exchangeRequest);
        ExchangeHistorySaveRequest exchangeHistorySaveRequest = new ExchangeHistorySaveRequest(
                exchangeRequest,
                response.getAmount(),
                response.getRate()
        );

        exchangeHistoryService.save(exchangeHistorySaveRequest);
        return response;
    }


    protected BulkCsvResponse bulkConvert(List<ExchangeRequest> exchangeRequests, String fileName) {
        List<ExchangeCsvResponse> responses = new LinkedList<>();

        for (ExchangeRequest exchangeRequest : exchangeRequests) {
            ExchangeResponse response = convert(exchangeRequest);
            ExchangeCsvResponse csvResponse = new ExchangeCsvResponse(response, exchangeRequest.getFrom().getCode(), exchangeRequest.getTo().getCode());
            responses.add(csvResponse);
        }

        return new BulkCsvResponse(fileName, responses);
    }

    @Transactional
    public List<BulkCsvResponse> processBulkCsv(MultipartFile[] files) throws IOException {
        List<BulkCsvResponse> responses = new LinkedList<>();

        for (MultipartFile file : files) {
            List<ExchangeRequest> exchangeRequests = CsvUtil.parseCsvFileToExchangeRequest(file);
            BulkCsvResponse bulkResponse = bulkConvert(exchangeRequests, file.getOriginalFilename());
            responses.add(bulkResponse);
        }

        return responses;
    }

    public ExchangeResponse convert(ExchangeRequest request) {
        return ValidationUtil.validateAndRun(request, () -> {
            Set<String> ids = Set.of(request.getFrom().getCode(), request.getTo().getCode());
            Map<String, Currency> currencies = currencyRepository.findByIdIn(ids);
            if (currencies.size() < 2) {
                throw new IllegalArgumentException("Unsupported currency code");
            }

            Currency fromCurrency = currencies.get(request.getFrom().getCode());
            Currency toCurrency = currencies.get(request.getTo().getCode());

            BigDecimal rate = toCurrency.getRate().divide(fromCurrency.getRate(), 8, RoundingMode.HALF_UP);
            BigDecimal rawResult = BigDecimal.valueOf(request.getAmount()).multiply(rate);
            long converted = rawResult.setScale(0, RoundingMode.HALF_UP).longValue();

            return new ExchangeResponse(converted, rate);
        });
    }
}
