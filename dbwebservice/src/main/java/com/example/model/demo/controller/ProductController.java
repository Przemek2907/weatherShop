package com.example.model.demo.controller;

import com.example.model.demo.dto.AddNewProductDto;
import com.example.model.demo.dto.ProductDto;
import com.example.model.demo.dto.ResponseDto;
import com.example.model.demo.model.Category;
import com.example.model.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/save")
    public ResponseEntity<ResponseDto> processExcelToLoadGoals(@ModelAttribute AddNewProductDto addNewProductDto) {
        Long savedProductId = null;
        try {
            savedProductId = productService.addNewProduct(addNewProductDto);
        }catch (Exception e) {
            return new ResponseEntity(ResponseDto.builder().errorMessage(e.getMessage()).build(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity(ResponseDto.builder().message("New product has been added. Id : " + savedProductId).build(), HttpStatus.CREATED);

    }

}
