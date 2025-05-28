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
    private final String from;
    private final String target;
    private final Long amount;
    private final Long convertedAmount;
    private final BigDecimal rate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime createdAt;
}
