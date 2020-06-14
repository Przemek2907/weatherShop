package com.example.model.demo.repo;

import com.example.model.demo.model.WeatherDictionary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherDictionaryRepository extends JpaRepository<WeatherDictionary, Long> {

    Optional<WeatherDictionary> findByWeatherDescription(String description);
}
