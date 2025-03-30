package com.example.demo.dto;

import lombok.Data;

@Data
public class WeatherResponseDto {
    private String city;
    private double temperature;
    private String description;   // 필드명을 condition으로 변경
}
