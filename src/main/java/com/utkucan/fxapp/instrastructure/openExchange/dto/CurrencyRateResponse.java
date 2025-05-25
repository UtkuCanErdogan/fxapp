package com.utkucan.fxapp.instrastructure.openExchange.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class CurrencyRateResponse {
    private String disclaimer;
    private String license;
    private String base;
    private Map<String, Double> rates;
}
