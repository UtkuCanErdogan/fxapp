package com.utkucan.fxapp.domain.repository;

import com.utkucan.fxapp.domain.entity.Currency;

import java.util.Set;

public interface CurrencyRepository {
    void saveAll(Set<Currency> rates);
    Currency findByCode(String code);
    Set<Currency> findAll();
}
