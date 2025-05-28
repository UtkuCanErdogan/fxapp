package com.utkucan.fxapp.adapter.rest;

import com.utkucan.fxapp.adapter.response.RestResponse;
import com.utkucan.fxapp.application.dto.request.CsvUploadRequest;
import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.BulkCsvResponse;
import com.utkucan.fxapp.application.dto.response.ExchangeHistoryDto;
import com.utkucan.fxapp.application.dto.response.ExchangeResponse;
import com.utkucan.fxapp.application.service.ExchangeHistoryService;
import com.utkucan.fxapp.application.service.ExchangeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/exchange")
@Slf4j
public class ExchangeController {

    private final ExchangeService exchangeService;
    private final ExchangeHistoryService exchangeHistoryService;

    public ExchangeController(ExchangeService exchangeService, ExchangeHistoryService exchangeHistoryService) {
        this.exchangeService = exchangeService;
        this.exchangeHistoryService = exchangeHistoryService;
    }

    @Operation(
            summary = "Retrieve conversion history",
            description = "Returns a paginated list of previous currency conversions based on optional filter parameters."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful retrieval of conversion history",
                    content = @Content(schema = @Schema(implementation = ExchangeHistoryDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid filter parameters",
                    content = @Content(schema = @Schema(implementation = RestResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = RestResponse.class))
            )
    })
    @GetMapping("/history")
    public ResponseEntity<RestResponse<Page<ExchangeHistoryDto>>> findAll(@ParameterObject ExchangeHistoryFilter filter) {
        log.info("[findAll] received with filter: {}", filter);
        Page<ExchangeHistoryDto> exchangeResponses = exchangeHistoryService.findAll(filter);
        log.info("[findAll] returned {} results", exchangeResponses.getTotalElements());
        return ResponseEntity.ok(RestResponse.success(exchangeResponses));
    }

    @Operation(
            summary = "Currency Conversion",
            description = "Converts an amount from one currency to another."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful conversion",
                    content = @Content(schema = @Schema(implementation = ExchangeResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = RestResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = RestResponse.class))
            )
    })
    @PostMapping("/convert")
    public ResponseEntity<RestResponse<ExchangeResponse>> convert(@RequestBody ExchangeRequest request) {
        log.info("[convert] received with request: {}", request);
        ExchangeResponse response = exchangeService.convertCurrencies(request);
        log.info("[convert] conversion result: {}", response);
        return ResponseEntity.ok(RestResponse.success(response));
    }

    @Operation(
            summary = "Bulk currency conversion with CSV upload",
            description = "Accepts up to 5 CSV files, each containing currency conversion requests in the format: source,target,amount. Returns the conversion results grouped by file."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful bulk conversion",
                    content = @Content(schema = @Schema(implementation = BulkCsvResponse.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid input data",
                    content = @Content(schema = @Schema(implementation = RestResponse.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(schema = @Schema(implementation = RestResponse.class))
            )
    })
    @PostMapping(value = "/convert-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RestResponse<List<BulkCsvResponse>>> convertCsv(@RequestPart(value = "file") MultipartFile[] files) {
        log.info("[convertCsv] received {} file(s): {}", files.length,
                Arrays.stream(files).map(MultipartFile::getOriginalFilename).toList());
        List<BulkCsvResponse> responses = exchangeService.processBulkCsv(new CsvUploadRequest(files));
        log.info("[convertCsv] processed {} entries in total", responses.size());
        return ResponseEntity.ok(RestResponse.success(responses));
    }
}
