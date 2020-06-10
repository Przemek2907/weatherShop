package com.example.model.demo.model;

import lombok.*;

import javax.persistence.*;


@Data
@Entity
public class ProductWeather {

    @EmbeddedId
    private ProductWeatherId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("weatherDictionaryId")
    private WeatherDictionary weatherDictionary;


    public ProductWeather() {
    }

    public ProductWeather(Product product, WeatherDictionary weatherDictionary) {
        this.id = new ProductWeatherId(product.getId(), weatherDictionary.getId());
        this.product = product;
        this.weatherDictionary = weatherDictionary;
    }
}
