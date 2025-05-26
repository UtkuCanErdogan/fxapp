package com.utkucan.fxapp.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeResponse {
    private BigDecimal convertedAmount;
    private BigDecimal rate;
}
