package com.example.model.demo;

import com.example.model.demo.model.Category;
import com.example.model.demo.model.Product;
import com.example.model.demo.model.WeatherDictionary;
import com.example.model.demo.repo.ProductRepository;
import com.example.model.demo.repo.WeatherDictionaryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@SpringBootApplication
public class DemoApplication {

    private final ProductRepository productRepository;
    private final WeatherDictionaryRepo weatherDictionaryRepo;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            if (weatherDictionaryRepo.count() == 0) {
                weatherDictionaryRepo.saveAll(List.of(
                        WeatherDictionary.builder()
                                .weatherDescription("RAINY")
                                .build(),
                        WeatherDictionary.builder()
                                .weatherDescription("SNOWY")
                                .build(),
                        WeatherDictionary.builder()
                                .weatherDescription("SUNNY")
                                .build(),
                        WeatherDictionary.builder()
                                .weatherDescription("WINDY")
                                .build(),
                        WeatherDictionary.builder()
                                .weatherDescription("COLD")
                                .build(),
                        WeatherDictionary.builder()
                                .weatherDescription("HOT")
                                .build(),
                        WeatherDictionary.builder()
                                .weatherDescription("WARM")
                                .build()
                        )
                );
            }
            if (productRepository.count() == 0) {
                productRepository.saveAll(List.of(
                        Product.builder()
                                .name("Playstation 4 Pro")
                                .category(Category.ELECTRONICS)
                                .price(BigDecimal.valueOf(1650L))
                                .build(),

                        Product.builder()
                                .name("Xbox One X")
                                .category(Category.ELECTRONICS)
                                .price(BigDecimal.valueOf(1450L))
                                .build(),

                        Product.builder()
                                .name("Bike")
                                .category(Category.TRANSPORT)
                                .price(BigDecimal.valueOf(580L))
                                .build(),

                        Product.builder()
                                .name("Sugar")
                                .category(Category.FOOD)
                                .price(BigDecimal.valueOf(3.50))
                                .build(),

                        Product.builder()
                                .name("Mercedes Benz")
                                .category(Category.TRANSPORT)
                                .price(BigDecimal.valueOf(230000L))
                                .build(),

                        Product.builder()
                                .name("Chicken")
                                .category(Category.FOOD)
                                .price(BigDecimal.valueOf(10L))
                                .build(),

                        Product.builder()
                                .name("BMW X5")
                                .category(Category.TRANSPORT)
                                .price(BigDecimal.valueOf(2300010L))
                                .build(),

                        Product.builder()
                                .name("Apple MacBook Pro")
                                .category(Category.ELECTRONICS)
                                .price(BigDecimal.valueOf(13500L))
                                .build()

                ));
            }

        };
    }


}
