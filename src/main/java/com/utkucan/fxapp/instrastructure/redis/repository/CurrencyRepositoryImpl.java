package com.utkucan.fxapp.instrastructure.redis.repository;

import com.utkucan.fxapp.domain.entity.Currency;
import com.utkucan.fxapp.domain.repository.CurrencyRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private final CurrencyRateAdapter adapter;

    public CurrencyRepositoryImpl(CurrencyRateAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public void saveAll(Set<Currency> rates) {
        adapter.saveAll(rates);
    }

    @Override
    public Currency findByCode(String code) {
        return adapter.findById(code).orElse(null);
    }

    @Override
    public Set<Currency> findAll() {
        Iterable<Currency> all = adapter.findAll();
        return StreamSupport.stream(all.spliterator(), false).collect(Collectors.toSet());
    }
}
