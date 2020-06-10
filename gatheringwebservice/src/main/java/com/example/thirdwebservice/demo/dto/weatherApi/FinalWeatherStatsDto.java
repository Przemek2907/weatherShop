package com.example.thirdwebservice.demo.dto.weatherApi;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class FinalWeatherStatsDto {

    private String latitude;
    private String longitude;
    private String timezone;
    private Double minTemperatureForPeriod;
    private Double maxTemperatureForPeriod;
    private Double averageMorningTemperatureForPeriod;
    private Double averageDayTemperatureForPeriod;
    private Double averageEveningTemperatureForPeriod;
    private Double averageNightTemperatureForPeriod;
    private Double averageWindSpeedForPeriod;
    private Double averageUVForPeriod;
    private Double averageRainForPeriod;
    private Map<String, Long> weatherDescriptionGroupedByOccurrence;



}
