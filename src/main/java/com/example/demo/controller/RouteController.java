package com.example.demo.controller;

import com.example.demo.model.LatLng;
import com.example.demo.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * /api/route 엔드포인트를 제공하는 REST 컨트롤러.
 * 출발지, 도착지 좌표 및 이동 모드를 받아 경로 좌표 배열을 반환한다.
 */
@RestController
@RequestMapping("/api/route")
@RequiredArgsConstructor
public class RouteController {

    private final RouteService routeService;

    /**
     * GET /api/route
     * @param originLat 출발지 위도
     * @param originLng 출발지 경도
     * @param destLat 도착지 위도
     * @param destLng 도착지 경도
     * @param mode 이동 모드 (기본값: driving)
     * @return 경로 좌표 배열(List<LatLng>)
     */
    @GetMapping
    public List<LatLng> getRoute(@RequestParam("originLat") double originLat,
                                 @RequestParam("originLng") double originLng,
                                 @RequestParam("destLat") double destLat,
                                 @RequestParam("destLng") double destLng,
                                 @RequestParam(value = "mode", defaultValue = "driving") String mode) {
        return routeService.getRoute(originLat, originLng, destLat, destLng, mode);
    }
}
