package com.utkucan.fxapp.application.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExchangeHistoryFilter {
    private String from;
    private String target;
    private Long minAmount;
    private Long maxAmount;
    private LocalDate startDate;
    private LocalDate endDate;

    private Integer limit;
    private Integer skip;
    private String orderBy;
    private String sortBy;
}
