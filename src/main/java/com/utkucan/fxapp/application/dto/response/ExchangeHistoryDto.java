package com.utkucan.fxapp.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Represents a historical currency conversion record.")
@Data
@AllArgsConstructor
public class ExchangeHistoryDto {
    @Schema(description = "Unique identifier for the conversion record", example = "f54e8d46-8c60-4a0c-8e0e-111111111111")
    private final UUID id;

    @Schema(description = "Source currency code (e.g., USD)", example = "USD")
    private final String from;

    @Schema(description = "Target currency code (e.g., TRY)", example = "TRY")
    private final String target;

    @Schema(description = "Amount to be converted in minor unit (e.g., cents)", example = "10000")
    private final Long amount;

    @Schema(description = "Converted amount in minor unit", example = "270000")
    private final Long convertedAmount;

    @Schema(description = "Exchange rate used for conversion", example = "27.0")
    private final BigDecimal rate;

    @Schema(description = "Timestamp when the conversion was made (yyyy-MM-dd HH:mm:ss)", example = "2025-05-28 09:30:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
}
