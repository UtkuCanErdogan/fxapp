package com.utkucan.fxapp.common.utils;

import com.utkucan.fxapp.application.dto.request.ExchangeRequest;
import com.utkucan.fxapp.common.exception.ExceptionCode;
import com.utkucan.fxapp.common.exception.FileReadException;
import com.utkucan.fxapp.common.exception.InvalidCurrencyException;
import com.utkucan.fxapp.domain.enums.CurrencyCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static List<ExchangeRequest> parseCsvFileToExchangeRequest(MultipartFile file)  {
        List<ExchangeRequest> requests = new ArrayList<>();

       try {
           BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
           String line;

           while ((line = reader.readLine()) != null) {
               String[] parts = line.split(",");
               if (parts.length != 3) throw new FileReadException(ExceptionCode.FILE_READ_EXCEPTION);;

               String from = parts[0].trim();
               String to = parts[1].trim();
               long amount = Long.parseLong(parts[2].trim());

               CurrencyCode fromCurrency = CurrencyCode.fromCode(from).orElseThrow(() -> new InvalidCurrencyException(
                       ExceptionCode.INVALID_CURRENCY_EXCEPTION));

               CurrencyCode toCurrency = CurrencyCode.fromCode(to).orElseThrow(() -> new InvalidCurrencyException(
                       ExceptionCode.INVALID_CURRENCY_EXCEPTION));;
               requests.add(new ExchangeRequest(fromCurrency, toCurrency, amount));
           }
       } catch (InvalidCurrencyException e) {
           throw e;
       }

       catch (Exception e) {
           throw new FileReadException(ExceptionCode.FILE_READ_EXCEPTION);
       }

        return requests;
    }
}
