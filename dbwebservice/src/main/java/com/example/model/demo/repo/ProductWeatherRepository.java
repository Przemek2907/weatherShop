package com.example.model.demo.repo;

import com.example.model.demo.model.ProductWeather;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWeatherRepository extends JpaRepository<ProductWeather, Long> {
}
