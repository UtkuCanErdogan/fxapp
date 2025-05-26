package com.utkucan.fxapp.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ExchangeHistoryDto {
    private final UUID id;
    private final String fromCurrency;
    private final String targetCurrency;
    private final BigDecimal originalAmount;
    private final BigDecimal convertedAmount;
    private final BigDecimal rateUsed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
}
