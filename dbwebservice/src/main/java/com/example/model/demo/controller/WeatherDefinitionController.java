package com.example.model.demo.controller;

import com.example.model.demo.dto.WeatherDefinitionDto;
import com.example.model.demo.service.WeatherDictionaryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/weather")
@RestController
public class WeatherDefinitionController {

    private final WeatherDictionaryService weatherDictionaryService;

    @GetMapping("/definitions")
    public List<WeatherDefinitionDto> getWeatherDefinitions() {
        return weatherDictionaryService.getAllWeatherDefinitions();
    }

}
