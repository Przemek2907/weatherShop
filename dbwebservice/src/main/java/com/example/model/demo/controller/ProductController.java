package com.example.model.demo.controller;

import com.example.model.demo.dto.ProductDto;
import com.example.model.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @GetMapping("/getProducts")
    private List<ProductDto> getAllProducts() {
        log.info("was I here in product definitions??");
        log.info("my products are definitions --> {}", productService.getAllProducts());
        return productService.getAllProducts();
    }
}
