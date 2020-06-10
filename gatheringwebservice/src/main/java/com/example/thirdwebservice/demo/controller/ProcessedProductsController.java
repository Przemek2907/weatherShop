package com.example.thirdwebservice.demo.controller;

import com.example.thirdwebservice.demo.dto.ProductDto;
import com.example.thirdwebservice.demo.dto.weatherApi.FinalWeatherStatsDto;
import com.example.thirdwebservice.demo.exceptions.AppException;
import com.example.thirdwebservice.demo.service.ProductsService;
import com.example.thirdwebservice.demo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
@CrossOrigin("http://localhost:3000")
@RestController
public class ProcessedProductsController {

    private final ProductsService productsService;
    private final WeatherService weatherService;

    @GetMapping("/currency/{code}")
    public ResponseEntity<List<ProductDto>> getProductsWithPricesInSelectedCurrency(@PathVariable("code") String code) {
        List<ProductDto> productsWithPricesInSelectedCurrency = null;
        try {
            productsWithPricesInSelectedCurrency = productsService.allProductsWithPricesInRequestedCurrency(code);
        } catch (AppException e) {
            log.info(e.getMessage());
            handleException(e.getMessage());
        }

        return new ResponseEntity<>(productsWithPricesInSelectedCurrency, HttpStatus.OK);
    }

    @GetMapping("/most_expensive")
    public ResponseEntity<Map<String, ProductDto>> getMostExpensiveInCategory() {
        Map<String, ProductDto> mostExpensiveInCategory = null;
        try {
            mostExpensiveInCategory = productsService.getMostExpensiveProductPerCategory();
        } catch (AppException e) {
            handleException(e.getMessage());
        }

        return new ResponseEntity<>(mostExpensiveInCategory, HttpStatus.OK);
    }

    @ExceptionHandler({ AppException.class })
    public ResponseEntity<AppException> handleException(String message) {
        return new ResponseEntity(message, HttpStatus.NO_CONTENT);
    }

    @GetMapping("/weather/{latitude}/{longitude}")
    public ResponseEntity<FinalWeatherStatsDto> getWeatherForecastStats(@PathVariable String latitude, @PathVariable String longitude) {

        return new ResponseEntity<>(weatherService.analyzeWeatherApiData(weatherService.getCurrentWeather(longitude, latitude)), HttpStatus.OK);
    }
}
