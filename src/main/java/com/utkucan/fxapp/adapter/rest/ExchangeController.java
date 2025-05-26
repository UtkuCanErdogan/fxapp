package com.utkucan.fxapp.adapter.rest;

import com.utkucan.fxapp.adapter.response.ApiResponse;
import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.application.dto.response.ExchangeResponse;
import com.utkucan.fxapp.application.service.ExchangeService;
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

    @PostMapping("/convert")
    public ResponseEntity<ApiResponse<ExchangeResponse>> convert(@RequestBody ExchangeRequest request) {
        ExchangeResponse response = exchangeService.convertCurrencies(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @PostMapping("/convert-csv")
    public ResponseEntity<ApiResponse<List<ExchangeResponse>>> convertCsv(@RequestParam("files") MultipartFile[] files) throws IOException {
        List<ExchangeResponse> responses = exchangeService.processBulkCsv(files);
        return ResponseEntity.ok(ApiResponse.success(responses));
    }
}
