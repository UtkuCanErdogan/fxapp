package com.utkucan.fxapp.application.service;

import com.utkucan.fxapp.application.dto.converter.ExchangeHistoryDtoConverter;
import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.application.dto.request.ExchangeHistorySaveRequest;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.ExchangeHistoryDto;
import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import com.utkucan.fxapp.domain.repository.ExchangeHistoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ExchangeHistoryService {

    private final ExchangeHistoryRepository exchangeHistoryRepository;

    public ExchangeHistoryService(ExchangeHistoryRepository exchangeHistoryRepository) {
        this.exchangeHistoryRepository = exchangeHistoryRepository;
    }

    public Page<ExchangeHistoryDto> findAll(ExchangeHistoryFilter filter) {
        return ExchangeHistoryDtoConverter.fromEntityPage(exchangeHistoryRepository.findAll(filter));
    }

    public void save(ExchangeHistorySaveRequest exchangeHistorySaveRequest) {
        ExchangeHistory exchangeHistory = new ExchangeHistory(exchangeHistorySaveRequest.getFrom().getCode(), exchangeHistorySaveRequest.getTo().getCode(),
                exchangeHistorySaveRequest.getAmount(), exchangeHistorySaveRequest.getConvertedAmount(), exchangeHistorySaveRequest.getRate());

        exchangeHistoryRepository.save(exchangeHistory);
    }
}
