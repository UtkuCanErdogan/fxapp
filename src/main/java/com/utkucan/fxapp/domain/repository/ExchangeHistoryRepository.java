package com.utkucan.fxapp.domain.repository;

import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import org.springframework.data.domain.Page;

import java.util.Set;

public interface ExchangeHistoryRepository {
    void save(ExchangeHistory history);
    Page<ExchangeHistory> findAll(ExchangeHistoryFilter filter);
}
