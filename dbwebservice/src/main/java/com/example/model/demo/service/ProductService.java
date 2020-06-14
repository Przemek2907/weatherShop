package com.example.model.demo.service;


import com.example.model.demo.dto.AddNewProductDto;
import com.example.model.demo.dto.ProductDto;
import com.example.model.demo.exceptions.AppException;
import com.example.model.demo.mapper.Mapper;
import com.example.model.demo.model.File;
import com.example.model.demo.model.Product;
import com.example.model.demo.model.ProductWeather;
import com.example.model.demo.repo.FileRepository;
import com.example.model.demo.repo.ProductRepository;
import com.example.model.demo.repo.ProductWeatherRepository;
import com.example.model.demo.repo.WeatherDictionaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final WeatherDictionaryRepository weatherDictionaryRepo;
    private final FileRepository fileRepository;
    private final MultipartFileService fileService;


    public List<ProductDto> getAllProducts() {
        List<ProductDto> productDtos = productRepository.findAll().stream()
                .map(Mapper::toProductDto)
                .collect(Collectors.toList());

        productDtos.forEach(this::addProductWeatherInfo);

        return productDtos;

    }

    public Long addNewProduct(AddNewProductDto addNewProductDto) {
        Product product = Mapper.toProduct(addNewProductDto);
        try {
            File productImage = new File();
            productImage.setFileName(fileService.addFile(addNewProductDto.getProductImage()));
            fileRepository.save(productImage);
            product.setFiles(Set.of(productImage));

            addNewProductDto.getWeatherCategory()
                    .forEach( id -> {
                        product.addWeatherCategory(weatherDictionaryRepo.getOne(id));
                    });
            log.info("Product before save : {}", product);
            productRepository.save(product);
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        return product.getId();
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
