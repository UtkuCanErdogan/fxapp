package com.utkucan.fxapp.application.dto.request;

import com.utkucan.fxapp.common.validation.DifferentCurrencies;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Schema(
        description = "Request object for converting currency."
)
@Data
@AllArgsConstructor
@DifferentCurrencies
public class ExchangeRequest {
    @NotNull(message = "Source currency is required")
    @Schema(description = "Source currency code", example = "USD", required = true)
    private CurrencyCode from;

    @NotNull(message = "Target currency is required")
    @Schema(description = "Target currency code", example = "TRY", required = true)
    private CurrencyCode target;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "100", message = "Amount must be at least 100")
    @DecimalMax(value = "10000000000", message = "Amount must not exceed 10000000000")
    @Schema(
            description = "Amount to be converted in minor unit (e.g., cents)",
            example = "10000",
            minimum = "100",
            maximum = "10000000000",
            required = true
    )
    private Long amount;
}
