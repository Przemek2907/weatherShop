package com.example.thirdwebservice.demo.dto.weatherApi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CurrentWeatherDto {

    // @JsonProperty("")
    // @JsonValue
    private String lat;
    private String lon;
    private String timezone;
    private List<DailyWeather> daily;

}
