package com.utkucan.fxapp.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ExchangeResponse {
    private final Long amount;
    private final BigDecimal rate;
}
