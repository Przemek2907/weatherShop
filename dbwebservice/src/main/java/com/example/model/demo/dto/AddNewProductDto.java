package com.example.model.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewProductDto {

    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private String currency;
    private Set<Long> weatherCategory;
    private MultipartFile productImage;

}
