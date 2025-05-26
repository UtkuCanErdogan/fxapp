package com.utkucan.fxapp.application.service;

import com.utkucan.fxapp.domain.enums.CurrencyCode;
import com.utkucan.fxapp.domain.entity.Currency;
import com.utkucan.fxapp.domain.repository.CurrencyRateProvider;
import com.utkucan.fxapp.domain.repository.CurrencyRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExchangeRateSyncService {

    private final CurrencyRateProvider currencyRateProvider;
    private final CurrencyRepository currencyRepository;

    public ExchangeRateSyncService(CurrencyRateProvider currencyRateProvider, CurrencyRepository currencyRepository) {
        this.currencyRateProvider = currencyRateProvider;
        this.currencyRepository = currencyRepository;
    }


    @Scheduled(fixedRateString = "${openExchangeRate.interval}")
    public void syncLatestRates() {
        CurrencyCode currencyCode = CurrencyCode.USD;
        Map<String, Double> rates = currencyRateProvider.getCurrentRates(currencyCode);

        long timestamp = System.currentTimeMillis();

        if (rates != null) {
            List<CurrencyCode> currencyCodes = CurrencyCode.getDataSet();
            Set<Currency> currencySet = currencyCodes.stream().map(c -> {
                Double rate = rates.get(c.toString());

                return new Currency(c.toString(), BigDecimal.valueOf(rate), currencyCode, timestamp);
            }).collect(Collectors.toSet());

            currencyRepository.saveAll(currencySet);
        } else {
            //ToDo: throw exception
        }
    }
}
