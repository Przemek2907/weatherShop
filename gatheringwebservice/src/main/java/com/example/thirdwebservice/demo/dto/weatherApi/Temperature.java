package com.example.thirdwebservice.demo.dto.weatherApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Temperature {

    private Double morn;
    private Double day;
    private Double eve;
    private Double night;
    private Double min;
    private Double max;

}
