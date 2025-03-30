package com.example.demo.util;

import com.example.demo.model.LatLng;
import java.util.ArrayList;
import java.util.List;

/**
 * Google Directions API에서 전달하는 인코딩된 polyline 문자열을
 * 디코딩하여 좌표(List<LatLng>)로 변환하는 유틸리티 클래스.
 */
public class PolylineDecoder {
    public static List<LatLng> decodePolyline(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            double finalLat = lat / 1E5;
            double finalLng = lng / 1E5;
            poly.add(new LatLng(finalLat, finalLng));
        }
        return poly;
    }

    // 단독 테스트용 main (필요시 실행)
    public static void main(String[] args) {
        String encodedPolyline = "sehdFok_fW`BfB~BbAp@qB[]u@_AUe@]}@Mm@Mm@IaACaA?cHEaDAa@nE~L";
        List<LatLng> path = decodePolyline(encodedPolyline);
        System.out.println("디코딩된 좌표 개수: " + path.size());
        for (LatLng point : path) {
            System.out.println("Lat: " + point.getLat() + ", Lng: " + point.getLng());
        }
    }
}
