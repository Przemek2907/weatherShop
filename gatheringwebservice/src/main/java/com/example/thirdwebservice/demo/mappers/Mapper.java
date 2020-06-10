package com.example.thirdwebservice.demo.mappers;

import com.example.thirdwebservice.demo.dto.weatherApi.CurrentWeatherDto;
import com.example.thirdwebservice.demo.dto.weatherApi.FinalWeatherStatsDto;

public interface Mapper {

     static FinalWeatherStatsDto fromWeatherApiResponseToFinalWeatherStats(CurrentWeatherDto currentWeatherDto) {
        return currentWeatherDto == null ? null : FinalWeatherStatsDto.builder()
                .latitude(currentWeatherDto.getLat())
                .longitude(currentWeatherDto.getLon())
                .timezone(currentWeatherDto.getTimezone())
                .build();
    }
}
