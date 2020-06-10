package com.example.thirdwebservice.demo.dto.weatherApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyWeather {

    private ZonedDateTime dt;
    private ZonedDateTime sunrise;
    private ZonedDateTime sunset;
    private Temperature temp;
    private Temperature feels_like;
    private Integer pressure;
    private Integer humidity;
    private Double dew_point;
    private Double wind_speed;
    private Integer wind_deg;
    private List<ShortSummary> weather;
    private Double clouds;
    private Double rain;
    private Double uvi;

}
