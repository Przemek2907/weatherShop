package com.example.thirdwebservice.demo.dto.weatherApi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShortSummary {

    private Long id;
    private String main;
    private String description;
    private String icon;
}
