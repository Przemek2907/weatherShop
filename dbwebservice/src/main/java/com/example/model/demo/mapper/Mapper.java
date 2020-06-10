package com.example.model.demo.mapper;

import com.example.model.demo.dto.ProductDto;
import com.example.model.demo.dto.WeatherDefinitionDto;
import com.example.model.demo.model.Product;
import com.example.model.demo.model.WeatherDictionary;

import java.util.ArrayList;
import java.util.HashSet;

public interface Mapper {

    static ProductDto toProductDto(Product product) {
        return product == null ? null : ProductDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .weatherDescription(new HashSet<>())
                .category(product.getCategory())
                .price(product.getPrice()).build();
    }

    static WeatherDictionary toWeatherDictionaryFromDescriptionKey(String weatherDescription) {
        return weatherDescription == null ? null : WeatherDictionary.builder()
                .productForWeather(new ArrayList<>())
                .weatherDescription(weatherDescription)
                .build();
    }

    static WeatherDefinitionDto toWeatherDefinition(WeatherDictionary weatherDictionary) {
        return weatherDictionary == null ? null : WeatherDefinitionDto.builder()
                .id(weatherDictionary.getId())
                .definition(weatherDictionary.getWeatherDescription())
                .build();
    }
}
