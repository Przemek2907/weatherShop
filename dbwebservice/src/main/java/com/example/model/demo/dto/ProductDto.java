package com.example.model.demo.dto;

import com.example.model.demo.model.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;
    private String name;
    private BigDecimal price;
    private Category category;
    private String description;
    private Set<String> weatherDescription;
}
