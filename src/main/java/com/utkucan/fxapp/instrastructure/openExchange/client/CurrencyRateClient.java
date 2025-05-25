package com.utkucan.fxapp.instrastructure.openExchange.client;

import com.utkucan.fxapp.domain.enums.CurrencyCode;
import com.utkucan.fxapp.domain.repository.CurrencyRateProvider;
import com.utkucan.fxapp.instrastructure.openExchange.dto.CurrencyRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class CurrencyRateClient implements CurrencyRateProvider {

    @Value("${openExchangeRate.apiUrl}")
    private String apiUrl;

    @Value("${openExchangeRate.apiKey}")
    private String apiKey;

    private final RestTemplate restTemplate;

    public CurrencyRateClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Map<String, Double> getCurrentRates(CurrencyCode code) {
        String url = UriComponentsBuilder
                .fromUriString(apiUrl)
                .queryParam("app_id", apiKey)
                .queryParam("base", code.toString())
                .toUriString();

        CurrencyRateResponse response = restTemplate.getForObject(url, CurrencyRateResponse.class);
        return response != null ? response.getRates() : null;
    }

}
