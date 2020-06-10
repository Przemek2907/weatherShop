package com.pzochowski.interview.service;

import com.pzochowski.interview.dto.NbpResponseDto;
import com.pzochowski.interview.dto.Rate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class NbpApi {

    private final RestTemplate restTemplate;

    @Value("${nbp.url}")
    private String url;

    private static final List<String> selectedCurrencies = List.of("EUR", "GBP", "USD");

    HttpHeaders httpHeaders = new HttpHeaders();

    HttpEntity httpEntity = new HttpEntity(httpHeaders);

    public Map<String, BigDecimal> getNbpData () {
        ResponseEntity<List<NbpResponseDto>> response = restTemplate.exchange(
                url, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<NbpResponseDto>>() {
                });

        return transformDataToCurrencyRateMap(Objects.requireNonNull(Objects.requireNonNull(response.getBody()).get(0)));
    }

    private Map<String, BigDecimal> transformDataToCurrencyRateMap(NbpResponseDto mappedApiResponse) {
        return mappedApiResponse.getRates().stream()
                .filter(currency -> selectedCurrencies.contains(currency.getCode()))
                .collect(Collectors.toMap(
                        Rate::getCode,
                        Rate::getMid
                ));
    }
}
