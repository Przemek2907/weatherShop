package com.example.model.demo.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Builder
@Embeddable
public class ProductWeatherId implements Serializable {

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "weatherDict_id")
    private Long weatherDictionaryId;


    public ProductWeatherId() {
    }

    public ProductWeatherId(Long productId, Long weatherDictionaryId) {
        this.productId = productId;
        this.weatherDictionaryId = weatherDictionaryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductWeatherId that = (ProductWeatherId) o;

        if (!Objects.equals(productId, that.productId)) return false;
        return Objects.equals(weatherDictionaryId, that.weatherDictionaryId);
    }

    @Override
    public int hashCode() {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (weatherDictionaryId != null ? weatherDictionaryId.hashCode() : 0);
        return result;
    }
}
