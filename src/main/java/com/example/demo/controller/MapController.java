package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MapController {

    @GetMapping("/map")
    public String showMap() {
        // src/main/resources/templates/map.html 파일이 반환됩니다.
        return "map";
    }
}
