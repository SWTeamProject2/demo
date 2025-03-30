package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 지도 좌표 정보를 담는 모델 클래스.
 */
@Data
@AllArgsConstructor
public class LatLng {
    private double lat;  // 위도
    private double lng;  // 경도
}
