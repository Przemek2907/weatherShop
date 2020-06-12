package com.example.model.demo.controller;

import com.example.model.demo.dto.ProductDto;
import com.example.model.demo.model.Category;
import com.example.model.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
@RequestMapping("/products")
@RestController
public class ProductController {

    private final ProductService productService;


    @GetMapping("/all")
    public List<ProductDto> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/categories")
    public List<String> getProductCategories() {
        return Arrays.stream(Category.values()).map(
                String::valueOf)
                .collect(Collectors.toList());
    }

}
