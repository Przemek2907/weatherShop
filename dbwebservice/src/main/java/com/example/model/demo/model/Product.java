package com.example.model.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
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
    private Category category;

    @Column
    private String description;

    @OneToMany(
            mappedBy = "product"
    )
    private Set<ProductWeather> weatherCategory = new HashSet<>();

}
