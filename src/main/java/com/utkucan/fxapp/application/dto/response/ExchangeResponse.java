package com.utkucan.fxapp.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Schema(description = "Currency conversion result")
public class ExchangeResponse {
    @Schema(description = "Converted amount in minor unit (e.g., cents)", example = "270000")
    private final Long amount;

    @Schema(description = "Currency exchange rate used for conversion", example = "27.0")
    private final BigDecimal rate;
}
