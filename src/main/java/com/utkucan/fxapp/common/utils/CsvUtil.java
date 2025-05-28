package com.utkucan.fxapp.common.utils;

import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static List<ExchangeRequest> parseCsvFileToExchangeRequest(MultipartFile file) throws IOException {
        List<ExchangeRequest> requests = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 3) continue;

            try {
                String from = parts[0].trim();
                String to = parts[1].trim();
                long amount = Long.parseLong(parts[2].trim());

                CurrencyCode fromCurrency = CurrencyCode.valueOf(from);
                CurrencyCode toCurrency = CurrencyCode.valueOf(to);
                requests.add(new ExchangeRequest(fromCurrency, toCurrency, amount));
            } catch (Exception e) {
                // log & skip
            }
        }

        return requests;
    }
}
