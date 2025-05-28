package com.utkucan.fxapp.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Represents a CSV-specific response that includes original currency codes.")
@Getter
public class ExchangeCsvResponse extends ExchangeResponse {
    @Schema(description = "Source currency code (e.g., USD)", example = "USD")
    private final String from;

    @Schema(description = "Target currency code (e.g., TRY)", example = "TRY")
    private final String target;

    @Schema(
            description = "The original amount requested for currency conversion, in subunits (e.g., cents or kuruş)",
            example = "10000"
    )
    private final Long requestedAmount;

    public ExchangeCsvResponse(ExchangeResponse exchangeResponse, String from, String to, Long requestedAmount) {
        super(exchangeResponse.getAmount(), exchangeResponse.getRate());
        this.from = from;
        this.target = to;
        this.requestedAmount = requestedAmount;
    }
}
