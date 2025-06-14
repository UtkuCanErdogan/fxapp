package com.utkucan.fxapp.application.dto.request;

import com.utkucan.fxapp.domain.enums.CurrencyCode;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ExchangeHistorySaveRequest extends ExchangeRequest {
    private final Long convertedAmount;
    private final BigDecimal rate;

    public ExchangeHistorySaveRequest(ExchangeRequest exchangeRequest, Long convertedAmount, BigDecimal rate) {
        super(exchangeRequest.getFrom(), exchangeRequest.getTarget(), exchangeRequest.getAmount());
        this.convertedAmount = convertedAmount;
        this.rate = rate;
    }
}
