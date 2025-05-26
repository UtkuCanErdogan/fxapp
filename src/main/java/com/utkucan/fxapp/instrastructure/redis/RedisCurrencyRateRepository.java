package com.utkucan.fxapp.instrastructure.redis;

import com.utkucan.fxapp.domain.entity.Currency;
import org.springframework.data.repository.CrudRepository;

public interface RedisCurrencyRateRepository extends CrudRepository<Currency, String> {
}
