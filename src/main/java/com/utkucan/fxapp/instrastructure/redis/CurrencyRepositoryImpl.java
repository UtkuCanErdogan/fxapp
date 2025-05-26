package com.utkucan.fxapp.instrastructure.redis;

import com.utkucan.fxapp.domain.entity.Currency;
import com.utkucan.fxapp.domain.repository.CurrencyRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final RedisCurrencyRateRepository adapter;

    public CurrencyRepositoryImpl(RedisCurrencyRateRepository adapter) {
        this.adapter = adapter;
    }

    @Override
    public void saveAll(Set<Currency> rates) {
        adapter.saveAll(rates);
    }

    @Override
    public Map<String, Currency> findByIdIn(Set<String> codes) {
        Iterable<Currency> currencies = adapter.findAllById(codes);
        return StreamSupport.stream(currencies.spliterator(), false)
                .collect(Collectors.toMap(Currency::getId, Function.identity()));
    }
}
