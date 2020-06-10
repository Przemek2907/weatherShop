package com.example.model.demo.service;


import com.example.model.demo.dto.ProductDto;
import com.example.model.demo.exceptions.AppException;
import com.example.model.demo.mapper.Mapper;
import com.example.model.demo.model.Product;
import com.example.model.demo.model.ProductWeather;
import com.example.model.demo.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;


    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = productRepository.findAll().stream()
                .map(Mapper::toProductDto)
                .collect(Collectors.toList());

        productDtos.forEach(this::addProductWeatherInfo);

        return productDtos;

    }

    /* TODO ta metoda ma zajmowac się pobieraniem opisu pogody (osobna tabela w bazie) , dla jakiej produkt jest odpowiedni
        a) pobieranie odbywa się poprzez relacje ManyToMany
        b) następnie metoda jest wykorzystywana w metodzie getAllProducts, dołączana do DTOsa, zeby wyświetlić w przeglądarce
        Czy to jest dobry sposób?
    */
    private void addProductWeatherInfo(ProductDto productDto) {

        if (productDto == null) {
            throw new AppException("There is no product Dto");
        }

        List<Product> products = productRepository.findAll();
        for(Product product : products) {
            if (product.getId().equals(productDto.getId())) {

                Set<ProductWeather> weatherForProduct = product.getWeatherCategory();

                for (ProductWeather singleProductWeather : weatherForProduct) {
                    productDto.getWeatherDescription().add(
                            singleProductWeather.getWeatherDictionary().getWeatherDescription()
                    );
                }
            }
        }
    }
}
