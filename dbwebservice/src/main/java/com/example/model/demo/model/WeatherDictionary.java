package com.example.model.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.Cache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@NaturalIdCache
@Cache(
        usage = CacheConcurrencyStrategy.READ_WRITE
)
public class WeatherDictionary {

    @Id
    @GeneratedValue
    private Long id;

    @NaturalId
    @Column(nullable = false, unique = true)
    private String weatherDescription;

    @OneToMany(
            mappedBy = "weatherDictionary"
    )
    private List<ProductWeather> productForWeather = new ArrayList<>();

}
