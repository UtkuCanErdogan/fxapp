package com.utkucan.fxapp.application.service;

import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.ExchangeResponse;
import com.utkucan.fxapp.domain.entity.Currency;
import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import com.utkucan.fxapp.domain.repository.CurrencyRepository;
import com.utkucan.fxapp.domain.repository.ExchangeHistoryRepository;
import com.utkucan.fxapp.instrastructure.utils.CsvUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ExchangeService {

    private final CurrencyRepository currencyRepository;
    private final ExchangeHistoryRepository exchangeHistoryRepository;

    public ExchangeService(CurrencyRepository currencyRepository, ExchangeHistoryRepository exchangeHistoryRepository) {
        this.currencyRepository = currencyRepository;
        this.exchangeHistoryRepository = exchangeHistoryRepository;
    }

    public ExchangeResponse convertCurrencies(ExchangeRequest exchangeRequest) {
        ExchangeResponse response = convert(exchangeRequest.getFrom(), exchangeRequest.getTo(), exchangeRequest.getAmount());

        ExchangeHistory exchangeHistory = ExchangeHistory.builder()
                .fromCurrency(exchangeRequest.getFrom().getCode())
                .targetCurrency(exchangeRequest.getTo().getCode())
                .originalAmount(exchangeRequest.getAmount())
                .convertedAmount(response.getConvertedAmount())
                .rateUsed(response.getRate())
                .build();

        exchangeHistoryRepository.save(exchangeHistory);
        return response;
    }


    protected List<ExchangeResponse> bulkConvert(List<ExchangeRequest> exchangeRequests) {
        List<ExchangeResponse> responses = new ArrayList<>();

        for (ExchangeRequest exchangeRequest : exchangeRequests) {
            ExchangeResponse response = convert(exchangeRequest.getFrom(), exchangeRequest.getTo(), exchangeRequest.getAmount());
            responses.add(response);
        }

        return responses;
    }

    @Transactional
    public List<ExchangeResponse> processBulkCsv(MultipartFile[] files) throws IOException {
        List<ExchangeResponse> responses = new ArrayList<>();

        for (MultipartFile file : files) {
            List<ExchangeRequest> exchangeRequests = CsvUtil.parseCsvFileToExchangeRequest(file);
            List<ExchangeResponse> bulkResponse = bulkConvert(exchangeRequests);
            responses.addAll(bulkResponse);
        }

        return responses;
    }

    private ExchangeResponse convert(CurrencyCode from, CurrencyCode to, BigDecimal amount) {
        Set<String> ids = Set.of(from.getCode(), to.getCode());
        Map<String, Currency> currencies = currencyRepository.findByIdIn(ids);
        if (currencies.size() < 2) {
            throw new IllegalArgumentException("Unsupported currency code");
        }

        Currency fromCurrency = currencies.get(from.getCode());
        Currency toCurrency = currencies.get(to.getCode());


        BigDecimal rate = toCurrency.getRate()
                .divide(fromCurrency.getRate(), 8, RoundingMode.HALF_UP);

        BigDecimal converted = amount.multiply(rate)
                .setScale(2, RoundingMode.HALF_UP);

        return new ExchangeResponse(rate, converted);
    }
}
