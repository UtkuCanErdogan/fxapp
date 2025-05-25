package com.utkucan.fxapp.instrastructure.redis.repository;

import com.utkucan.fxapp.domain.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface CurrencyRateAdapter extends CrudRepository<Currency, String> {
}
