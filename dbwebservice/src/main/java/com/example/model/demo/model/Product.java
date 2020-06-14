package com.example.model.demo.model;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;

    @Column
    private BigDecimal price;

    @Column
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column
    private String description;

    @OneToMany(
            mappedBy = "product",
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ProductWeather> weatherCategory = new HashSet<>();

    @OneToMany
    private Set<File> files;

    public void addWeatherCategory (WeatherDictionary weatherDictionary) {
        ProductWeather productWeather = new ProductWeather(this, weatherDictionary);
        weatherCategory.add(productWeather);
        weatherDictionary.getProductForWeather().add(productWeather);
    }

    public void removeWeatherCategoryFromProduct(WeatherDictionary weatherDictionary) {

        Iterator<ProductWeather> productWeatherIterator = weatherCategory.iterator();

        while (productWeatherIterator.hasNext()) {
            ProductWeather productWeather = productWeatherIterator.next();

            if (productWeather.getProduct().equals(this) && productWeather.getWeatherDictionary().equals(weatherDictionary)) {
                productWeatherIterator.remove();
                productWeather.getProduct().getWeatherCategory().remove(productWeather);
                productWeather.setProduct(null);
                productWeather.setWeatherDictionary(null);
            }
        }
    }

}
