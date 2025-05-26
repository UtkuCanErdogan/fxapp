package com.utkucan.fxapp.application.dto.request;

import com.utkucan.fxapp.domain.enums.CurrencyCode;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeRequest {
    private CurrencyCode from;
    private CurrencyCode to;
    private BigDecimal amount;
}
