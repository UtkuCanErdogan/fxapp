package com.utkucan.fxapp.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Schema(description = "Represents the response for a single CSV file containing multiple currency conversion results.")
@Data
@AllArgsConstructor
public class BulkCsvResponse {
    @Schema(description = "Name of the uploaded CSV file", example = "example_file.csv")
    private final String fileName;

    @Schema(description = "List of conversion results from the CSV file")
    private final List<ExchangeCsvResponse> exchangeResults;
}
