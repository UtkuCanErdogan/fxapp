package com.utkucan.fxapp.adapter.rest;

import com.utkucan.fxapp.adapter.response.ApiResponse;
import com.utkucan.fxapp.application.dto.request.CsvUploadRequest;
import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.BulkCsvResponse;
import com.utkucan.fxapp.application.dto.response.ExchangeHistoryDto;
import com.utkucan.fxapp.application.dto.response.ExchangeResponse;
import com.utkucan.fxapp.application.service.ExchangeHistoryService;
import com.utkucan.fxapp.application.service.ExchangeService;
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

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<Page<ExchangeHistoryDto>>> findAll(@ParameterObject ExchangeHistoryFilter filter) {
        log.info("[findAll] received with filter: {}", filter);
        Page<ExchangeHistoryDto> exchangeResponses = exchangeHistoryService.findAll(filter);
        log.info("[findAll] returned {} results", exchangeResponses.getTotalElements());
        return ResponseEntity.ok(ApiResponse.success(exchangeResponses));
    }

    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<ExchangeResponse>> convert(@RequestBody ExchangeRequest request) {
        log.info("[convert] received with request: {}", request);
        ExchangeResponse response = exchangeService.convertCurrencies(request);
        log.info("[convert] conversion result: {}", response);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping(value = "/convert-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<BulkCsvResponse>>> convertCsv(@RequestPart(value = "file") MultipartFile[] files) {
        log.info("[convertCsv] received {} file(s): {}", files.length,
                Arrays.stream(files).map(MultipartFile::getOriginalFilename).toList());
        List<BulkCsvResponse> responses = exchangeService.processBulkCsv(new CsvUploadRequest(files));
        log.info("[convertCsv] processed {} entries in total", responses.size());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
