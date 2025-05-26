package com.utkucan.fxapp.adapter.rest;

import com.utkucan.fxapp.adapter.response.ApiResponse;
import com.utkucan.fxapp.application.dto.request.ExchangeHistoryFilter;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.BulkCsvResponse;
import com.utkucan.fxapp.application.dto.response.ExchangeHistoryDto;
import com.utkucan.fxapp.application.dto.response.ExchangeResponse;
import com.utkucan.fxapp.application.service.ExchangeService;
import com.utkucan.fxapp.domain.entity.ExchangeHistory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/exchange")
public class ExchangeController {

    private final ExchangeService exchangeService;

    public ExchangeController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<Page<ExchangeHistoryDto>>> findAll(@ParameterObject ExchangeHistoryFilter filter) {
        Page<ExchangeHistoryDto> exchangeResponses = exchangeService.findAll(filter);
        return ResponseEntity.ok(ApiResponse.success(exchangeResponses));
    }

    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<ExchangeResponse>> convert(@RequestBody ExchangeRequest request) {
        ExchangeResponse response = exchangeService.convertCurrencies(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping(value = "/convert-csv", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<List<BulkCsvResponse>>> convertCsv(@RequestPart(value = "file") MultipartFile[] files) throws IOException {
        List<BulkCsvResponse> responses = exchangeService.processBulkCsv(files);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
