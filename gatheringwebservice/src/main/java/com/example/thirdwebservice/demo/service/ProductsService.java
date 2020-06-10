package com.example.thirdwebservice.demo.service;

import com.example.thirdwebservice.demo.dto.ProductDto;
import com.example.thirdwebservice.demo.dto.weatherApi.FinalWeatherStatsDto;
import com.example.thirdwebservice.demo.exceptions.AppException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductsService {

    private final RestTemplate restTemplate;

    private HttpHeaders httpHeaders = new HttpHeaders();



    @Value("${products.webservice.url}")
    private String productsServiceApi;

    @Value("${nbp.webservice.url}")
    private String npbCurrencyRatingApi;

    private Map<String, BigDecimal> getCurrencyRates() {

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Map<String,BigDecimal>> currencyRates = restTemplate.exchange(
                npbCurrencyRatingApi, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<Map<String, BigDecimal>>() {
                }
        );

        return currencyRates.getBody();
    }

    private List<ProductDto> getListOfProducts() {


        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<List<ProductDto>> productsList = restTemplate.exchange(
                productsServiceApi, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<ProductDto>>() {
                }
        );

        return productsList.getBody();
    }

    public List<ProductDto> allProductsWithPricesInRequestedCurrency(String currencyCode) {

        if (currencyCode == null) {
            throw new AppException("There is no currency name in the request");
        }

        Map<String, BigDecimal> currencyRates = getCurrencyRates();

        if (!currencyRates.containsKey(currencyCode)) {
            throw new AppException("There is no currency code matching the requested one to provide currency rating");
        }

        BigDecimal ratingOfRequestedCurrency = currencyRates.get(currencyCode);

        log.info("Requested currency and rating for each prices will be recalculated : {} ---> {}", currencyCode, ratingOfRequestedCurrency);

        return getListOfProducts().stream()
                .map(productFromApi -> ProductDto.builder()
                .name(productFromApi.getName())
                .price(productFromApi.getPrice().divide(ratingOfRequestedCurrency, RoundingMode.valueOf(2)))
                        .currency(currencyCode)
                        .category(productFromApi.getCategory())
                        .build())
                .collect(Collectors.toList());
    }

    public Map<String, ProductDto> getMostExpensiveProductPerCategory() {

        return getListOfProducts().stream()
                .map(productFromApi -> ProductDto.builder()
                        .name(productFromApi.getName())
                        .category(productFromApi.getCategory())
                        .price(productFromApi.getPrice())
                        .build()
                )
                .collect(Collectors.groupingBy(
                        ProductDto::getCategory
                ))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        productList -> productList.getValue().stream().max(
                                //TODO what to do in case of more than one product having the highest price
                                Comparator.comparing(ProductDto::getPrice)
                        ).orElseThrow(() -> new AppException("Could not single out one product with the biggest price in the category"))
                ));
    }

    public List<ProductDto> getProductsSuitableForWeatherForecast(FinalWeatherStatsDto finalWeatherStatsDto) {

        if (finalWeatherStatsDto == null) {
            return getListOfProducts();
        }

        //finalWeatherStatsDto.getWeatherDescriptionGroupedByOccurrence()

        return getListOfProducts();
    }
}
