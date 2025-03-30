package com.example.demo.service;

import com.example.demo.dto.WeatherResponseDto;
import com.example.demo.util.JSONAPICall;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class WeatherService {

    public WeatherResponseDto getWeather(double lat, double lon) {
        try {
            // lat, lng는 필요에 따라 URL에 동적으로 반영할 수 있습니다.
            // 여기서는 예시로 Seoul의 날씨를 고정으로 호출합니다.
            String response = JSONAPICall.callApi(lat, lon);
            System.out.println("API 응답 원문: ");
            System.out.println(response);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(response);

            WeatherResponseDto dto = new WeatherResponseDto();
            dto.setCity(root.path("name").asText());
            dto.setTemperature(root.path("main").path("temp").asDouble());
            if (root.has("weather") && root.path("weather").isArray() && root.path("weather").size() > 0) {
                dto.setDescription(root.path("weather").get(0).path("description").asText());

            } else {
                dto.setDescription("정보없음");
            }
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            WeatherResponseDto errorDto = new WeatherResponseDto();
            errorDto.setCity("N/A");
            errorDto.setTemperature(0.0);
            errorDto.setDescription("API 호출 실패");
            return errorDto;
        }
    }
}
