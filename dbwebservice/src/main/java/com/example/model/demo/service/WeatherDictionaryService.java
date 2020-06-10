package com.example.model.demo.service;

import com.example.model.demo.dto.WeatherDefinitionDto;
import com.example.model.demo.exceptions.AppException;
import com.example.model.demo.mapper.Mapper;
import com.example.model.demo.model.WeatherDictionary;
import com.example.model.demo.repo.WeatherDictionaryRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class WeatherDictionaryService {

    private final WeatherDictionaryRepo weatherDictionaryRepo;

    //TODO dlaczego klasy serwisowe dzialaly z wyciaganiem z bazy danych bez adnotacji transactional??

    /* TODO jak to rozegrac - chce przechwycic dane z weather api, juz przetworzone przez mój webservice
        i jeśli nie istnieje jeszcze taki description, wtedy zapisać go w bazie

     */


    public void addNewWeatherDefinitions(Map<String, Long> weatherDescriptionFromApi) {

        if (weatherDescriptionFromApi == null) {
            throw new AppException("No data to process");
        }

        weatherDescriptionFromApi.keySet().forEach(
               key -> weatherDictionaryRepo.findByWeatherDescription(key).ifPresentOrElse(
                       null , () ->
                           weatherDictionaryRepo.save(Mapper.toWeatherDictionaryFromDescriptionKey(key))
               )
        );

    }

    public List<WeatherDefinitionDto> getAllWeatherDefinitions() {
        return weatherDictionaryRepo.findAll().stream()
                .map(Mapper::toWeatherDefinition)
                .collect(Collectors.toList());
    }

}
