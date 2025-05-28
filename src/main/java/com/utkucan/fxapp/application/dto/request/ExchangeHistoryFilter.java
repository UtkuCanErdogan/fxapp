package com.utkucan.fxapp.application.dto.request;

import com.utkucan.fxapp.common.dto.Pagination;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ExchangeHistoryFilter extends Pagination {
    @Schema(description = "Source currency", example = "USD")
    private CurrencyCode from;

    @Schema(description = "Target currency", example = "TRY")
    private CurrencyCode target;

    @Min(value = 100, message = "Minimum amount must be at least 100")
    @Schema(description = "Minimum converted amount", example = "1000")
    private Long minAmount;

    @Max(value = 10000000000L, message = "Maximum amount must not exceed 10,000,000,000")
    @Schema(description = "Maximum converted amount", example = "1000000")
    private Long maxAmount;

    @Schema(description = "Start date of the search range", example = "2024-01-01")
    private LocalDate startDate;

    @Schema(description = "End date of the search range", example = "2024-12-31")
    private LocalDate endDate;

    @Schema(description = "Field to sort by", example = "createdAt", allowableValues = {"createdAt", "amount", "convertedAmount"})
    private String orderBy;
}
