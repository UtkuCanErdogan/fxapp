package com.utkucan.fxapp.domain.repository;

import com.utkucan.fxapp.domain.enums.CurrencyCode;

import java.util.Map;

public interface CurrencyRateProvider {
    Map<String, Double> getCurrentRates(CurrencyCode code);
}
