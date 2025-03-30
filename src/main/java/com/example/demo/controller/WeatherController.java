package com.example.demo.controller;

import com.example.demo.dto.WeatherResponseDto;
import com.example.demo.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping
    public WeatherResponseDto getWeather(@RequestParam("lat") double lat,
                                         @RequestParam("lng") double lng) {
        return weatherService.getWeather(lat, lng);
    }
}
