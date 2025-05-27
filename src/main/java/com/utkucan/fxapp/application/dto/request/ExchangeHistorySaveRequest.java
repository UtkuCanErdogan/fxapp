package com.utkucan.fxapp.application.dto.request;

import com.utkucan.fxapp.domain.enums.CurrencyCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeHistorySaveRequest extends ExchangeRequest {
    private final BigDecimal convertedAmount;
    private final BigDecimal rate;

    public ExchangeHistorySaveRequest(ExchangeRequest exchangeRequest, BigDecimal convertedAmount, BigDecimal rate) {
        super(exchangeRequest.getFrom(), exchangeRequest.getTo(), exchangeRequest.getAmount());
        this.convertedAmount = convertedAmount;
        this.rate = rate;
    }
}
