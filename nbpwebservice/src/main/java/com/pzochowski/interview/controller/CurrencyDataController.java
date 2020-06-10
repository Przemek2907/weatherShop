package com.pzochowski.interview.controller;

import com.pzochowski.interview.service.NbpApi;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class CurrencyDataController {

    public final NbpApi nbpApi;


    @GetMapping("/nbpData")
    public Map<String, BigDecimal> getData() {
        return nbpApi.getNbpData();
    }
}
