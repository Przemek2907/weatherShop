package com.pzochowski.interview.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NbpResponseDto {

    private String table;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;
}
