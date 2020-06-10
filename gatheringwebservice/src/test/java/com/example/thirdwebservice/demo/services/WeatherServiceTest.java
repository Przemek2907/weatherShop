package com.example.thirdwebservice.demo.services;

import com.example.thirdwebservice.demo.service.WeatherService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeatherServiceTest {


    @Autowired
    private WeatherService weatherService;

    @Test
    @DisplayName("Test basic connection and response from open weather Api")
    public void whenOpenWeatherApiCalled_ThenJsonResponseReturned() {
        System.out.println(weatherService.getCurrentWeather("105", "60"));
    }

    @Test
    @DisplayName("When data is returned from weather API, then all weather details are calculated")
    public void whenOpenWeatherApiResponds_ThenCalculateWeatherDetails() {
        System.out.println(weatherService.analyzeWeatherApiData(weatherService.getCurrentWeather("20", "52")));
    }
}
