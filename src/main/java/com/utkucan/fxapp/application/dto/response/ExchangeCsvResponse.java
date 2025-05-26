package com.utkucan.fxapp.application.dto.response;

import lombok.Getter;

import java.math.BigDecimal;


@Getter
public class ExchangeCsvResponse extends ExchangeResponse {
    private final String from;
    private final String to;

    public ExchangeCsvResponse(ExchangeResponse exchangeResponse, String from, String to) {
        super(exchangeResponse.getConvertedAmount(), exchangeResponse.getRate());
        this.from = from;
        this.to = to;
    }
}
