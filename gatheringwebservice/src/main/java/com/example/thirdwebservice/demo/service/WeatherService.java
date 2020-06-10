package com.example.thirdwebservice.demo.service;

import com.example.thirdwebservice.demo.dto.weatherApi.CurrentWeatherDto;
import com.example.thirdwebservice.demo.dto.weatherApi.DailyWeather;
import com.example.thirdwebservice.demo.dto.weatherApi.FinalWeatherStatsDto;
import com.example.thirdwebservice.demo.dto.weatherApi.ShortSummary;
import com.example.thirdwebservice.demo.exceptions.AppException;
import com.example.thirdwebservice.demo.mappers.Mapper;
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

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    HttpHeaders httpHeaders = new HttpHeaders();

    @Value("${openweather.api.url}")
    private String openWeatherApi;

    @Value("${openweather.api.key}")
    private String apiKey;


    public CurrentWeatherDto getCurrentWeather(String longitude, String latitude) {

        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        String weatherForGeolocation = openWeatherApi + "lat=" + latitude + "&lon=" + longitude +
                "&units=metric" + "&lang=pl"
                + "&exclude=current,hourly"  +"&appid=" + apiKey;

        ResponseEntity<CurrentWeatherDto> weatherResponse = restTemplate.exchange(weatherForGeolocation, HttpMethod.GET, httpEntity, new ParameterizedTypeReference<CurrentWeatherDto>() {
                }
        );

        return weatherResponse.getBody();
    }

    public FinalWeatherStatsDto analyzeWeatherApiData(CurrentWeatherDto currentWeatherDto) {

        if (currentWeatherDto == null) {
            throw new AppException("Data from Weather Api is not available");
        }

        FinalWeatherStatsDto weatherStatsForPeriodAnalyzed = Mapper.fromWeatherApiResponseToFinalWeatherStats(currentWeatherDto);

        calculateTemperatureStatsForPeriod(currentWeatherDto, weatherStatsForPeriodAnalyzed);
        //calculateAverageRain(currentWeatherDto, weatherStatsForPeriodAnalyzed);
        calculateAverageUVForPeriod(currentWeatherDto, weatherStatsForPeriodAnalyzed);
        calculateAverageWindSpeed(currentWeatherDto, weatherStatsForPeriodAnalyzed);
        calculatePrevalentWeatherDescription(currentWeatherDto, weatherStatsForPeriodAnalyzed);


        return weatherStatsForPeriodAnalyzed;
    }

    private void calculateTemperatureStatsForPeriod(CurrentWeatherDto currentWeatherDto, FinalWeatherStatsDto finalWeatherStatsDto) {

        Double morningTemperatureSumOverPeriod =  currentWeatherDto.getDaily().stream()
                .map(singleDay -> singleDay.getFeels_like().getMorn())
                .reduce(0D, Double::sum);

        Double dayTemperatureSumOverPeriod =  currentWeatherDto.getDaily().stream()
                .map(singleDay -> singleDay.getFeels_like().getDay())
                .reduce(0D, Double::sum);

        Double eveningTemperatureSumOverPeriod =  currentWeatherDto.getDaily().stream()
                .map(singleDay -> singleDay.getFeels_like().getEve())
                .reduce(0D, Double::sum);

        Double nightTemperatureSumOverPeriod =  currentWeatherDto.getDaily().stream()
                .map(singleDay -> singleDay.getFeels_like().getNight())
                .reduce(0D, Double::sum);

        Double minimumTemperatureForPeriod =  currentWeatherDto.getDaily().stream()
                .map(singleDay -> singleDay.getTemp().getMin())
                .collect(Collectors.toList())
                .stream()
                .min(Comparator.comparingDouble(Double::doubleValue))
                .orElseThrow(() -> new AppException("Could not calculate minimum temperature over given period"));

        Double maximumTemperatureForPeriod =  currentWeatherDto.getDaily().stream()
                .map(singleDay -> singleDay.getTemp().getMax())
                .collect(Collectors.toList())
                .stream()
                .max(Comparator.comparingDouble(Double::doubleValue))
                .orElseThrow(() -> new AppException("Could not calculate maximum temperature over given period"));


        int amountOfDaysInPeriod = currentWeatherDto.getDaily().size();

        finalWeatherStatsDto.setAverageMorningTemperatureForPeriod(morningTemperatureSumOverPeriod/amountOfDaysInPeriod);
        finalWeatherStatsDto.setAverageDayTemperatureForPeriod(dayTemperatureSumOverPeriod/amountOfDaysInPeriod);
        finalWeatherStatsDto.setAverageEveningTemperatureForPeriod(eveningTemperatureSumOverPeriod/amountOfDaysInPeriod);
        finalWeatherStatsDto.setAverageNightTemperatureForPeriod(nightTemperatureSumOverPeriod/amountOfDaysInPeriod);

        finalWeatherStatsDto.setMinTemperatureForPeriod(minimumTemperatureForPeriod);
        finalWeatherStatsDto.setMaxTemperatureForPeriod(maximumTemperatureForPeriod);

    }

    private void calculateAverageWindSpeed(CurrentWeatherDto currentWeatherDto, FinalWeatherStatsDto finalWeatherStatsDto) {

        Double sumOfWindSpeedOverPeriod = currentWeatherDto.getDaily().stream()
                .map(DailyWeather::getWind_speed)
                .reduce(0D, Double::sum);

        finalWeatherStatsDto.setAverageWindSpeedForPeriod(sumOfWindSpeedOverPeriod/currentWeatherDto.getDaily().size());
    }

    private void calculateAverageRain(CurrentWeatherDto currentWeatherDto, FinalWeatherStatsDto finalWeatherStatsDto) {

        Double sumOfRainOverThePeriod = currentWeatherDto.getDaily().stream()
                .filter(Objects::nonNull)
                .map(DailyWeather::getRain)
                .reduce(0D, Double::sum);

        finalWeatherStatsDto.setAverageRainForPeriod(sumOfRainOverThePeriod/currentWeatherDto.getDaily().size());
    }

    private void calculateAverageUVForPeriod(CurrentWeatherDto currentWeatherDto, FinalWeatherStatsDto finalWeatherStatsDto) {

        Double sumOfUVforPeriod = currentWeatherDto.getDaily().stream()
                .map(DailyWeather::getUvi)
                .reduce(0D, Double::sum);

        finalWeatherStatsDto.setAverageRainForPeriod(sumOfUVforPeriod/currentWeatherDto.getDaily().size());
    }

    private void calculatePrevalentWeatherDescription(CurrentWeatherDto currentWeatherDto, FinalWeatherStatsDto finalWeatherStatsDto) {

        Map<String, Long> weatherDescriptionGroupedByOccurrence = currentWeatherDto.getDaily()
                .stream()
                .flatMap(singleDay -> singleDay.getWeather().stream().map(ShortSummary::getDescription))
                .collect(Collectors.groupingBy(description -> description, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Comparator.comparingLong(Map.Entry::getValue))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (v1,v2) -> v1,
                        LinkedHashMap::new
                ));

        finalWeatherStatsDto.setWeatherDescriptionGroupedByOccurrence(weatherDescriptionGroupedByOccurrence);
    }


}
