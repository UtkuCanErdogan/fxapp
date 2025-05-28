package com.utkucan.fxapp.application.dto.request;

import com.utkucan.fxapp.common.validation.DifferentCurrencies;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@DifferentCurrencies
public class ExchangeRequest {
    @NotNull(message = "Source currency is required")
    private CurrencyCode from;

    @NotNull(message = "Target currency is required")
    private CurrencyCode to;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "100", message = "Amount must be at least 100")
    @DecimalMax(value = "10000000000", message = "Amount must not exceed 10000000000")
    private Long amount;
}
