package com.utkucan.fxapp.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BulkCsvResponse {
    private final String fileName;
    private final List<ExchangeCsvResponse> exchangeResults;
}
