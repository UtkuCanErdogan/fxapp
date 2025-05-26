package com.utkucan.fxapp.domain.repository;

import com.utkucan.fxapp.domain.entity.Currency;

import java.util.Map;
import java.util.Set;

public interface CurrencyRepository {
    void saveAll(Set<Currency> rates);
    Map<String, Currency> findByIdIn(Set<String> codes);
}
