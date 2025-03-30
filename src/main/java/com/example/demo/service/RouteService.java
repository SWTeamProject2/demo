package com.example.demo.service;

import com.example.demo.model.LatLng;
import com.example.demo.util.PolylineDecoder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Collections;
import java.util.List;

/**
 * Google Directions API를 호출하여 출발지, 도착지 및 이동 모드에 따른 경로 데이터를 처리하는 서비스 클래스.
 * 인코딩된 polyline 문자열을 디코딩하여 좌표 배열(List<LatLng>)로 반환합니다.
 */
@Service
public class RouteService {

    // Google Directions API 키 (실제 발급받은 키로 교체)
    private final String GOOGLE_API_KEY = "APIKEY_HERE";

    /**
     * 주어진 출발지와 도착지 좌표, 이동 모드에 따라 경로 좌표 배열을 반환한다.
     *
     * @param originLat 출발지 위도
     * @param originLng 출발지 경도
     * @param destLat   도착지 위도
     * @param destLng   도착지 경도
     * @param mode      이동 모드 ("driving", "walking", "transit" 등, 소문자로 전달)
     * @return 경로 좌표 배열(List<LatLng>); 경로가 없으면 빈 배열 반환
     */
    public List<LatLng> getRoute(double originLat, double originLng, double destLat, double destLng, String mode) {
        try {
            // 요청 URL에 region=kr 추가: 한국 내 경로 검색 명시
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin="
                    + originLat + "," + originLng
                    + "&destination=" + destLat + "," + destLng
                    + "&mode=" + mode
                    + "&region=kr"
                    + "&key=" + GOOGLE_API_KEY;
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);

            System.out.println("Google Directions API 응답:");
            System.out.println(response);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);
            JsonNode routes = root.path("routes");
            if (routes.isArray() && routes.size() > 0) {
                JsonNode route = routes.get(0);
                JsonNode overviewPolyline = route.path("overview_polyline");
                String encodedPolyline = overviewPolyline.path("points").asText();
                System.out.println("Encoded Polyline: " + encodedPolyline);
                if (encodedPolyline == null || encodedPolyline.isEmpty()) {
                    System.err.println("인코딩된 polyline 데이터가 없습니다.");
                    return Collections.emptyList();
                }
                List<LatLng> path = PolylineDecoder.decodePolyline(encodedPolyline);
                System.out.println("디코딩된 경로 좌표 수: " + path.size());
                return path;
            } else {
                System.err.println("경로를 찾을 수 없습니다. 상태: " + root.path("status").asText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
