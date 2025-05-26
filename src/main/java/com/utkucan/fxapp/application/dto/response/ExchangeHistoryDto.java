package com.utkucan.fxapp.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeHistoryDto {
    private UUID id;
    private String fromCurrency;
    private String targetCurrency;
    private BigDecimal originalAmount;
    private BigDecimal convertedAmount;
    private BigDecimal rateUsed;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
}
