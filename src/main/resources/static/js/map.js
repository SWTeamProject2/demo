// map.js
var map, directionsService, directionsRenderer, geocoder;
var originMarker, destinationMarker;

function initMap() {
  map = new google.maps.Map(document.getElementById("map"), {
    center: {lat: 37.5665, lng: 126.9780},
    zoom: 14
  });

  directionsService = new google.maps.DirectionsService();
  directionsRenderer = new google.maps.DirectionsRenderer({ suppressMarkers: true });
  directionsRenderer.setMap(map);

  geocoder = new google.maps.Geocoder();

  document.getElementById("routeBtn").addEventListener("click", function() {
    var originText = document.getElementById("originInput").value;
    var destinationText = document.getElementById("destinationInput").value;
    if (!originText || !destinationText) {
      alert("출발지와 도착지를 모두 입력하세요.");
      return;
    }
    geocodeAddress(originText, function(originLatLng) {
      geocodeAddress(destinationText, function(destinationLatLng) {
        // 기존 마커 제거
        if (originMarker) originMarker.setMap(null);
        if (destinationMarker) destinationMarker.setMap(null);
        originMarker = new google.maps.Marker({
          position: originLatLng,
          map: map,
          label: "A"
        });
        destinationMarker = new google.maps.Marker({
          position: destinationLatLng,
          map: map,
          label: "B"
        });
        document.getElementById("clickInfo").innerHTML =
          "출발지: " + originText + "<br>도착지: " + destinationText;
        // 경로 요청 (route.js)
        findRoutes(originLatLng, destinationLatLng);
        // 날씨 정보 호출: 출발지와 도착지 모두
        getOriginWeather(originLatLng.lat(), originLatLng.lng());
        getDestinationWeather(destinationLatLng.lat(), destinationLatLng.lng());
      });
    });
  });
}

function geocodeAddress(address, callback) {
  geocoder.geocode({ address: address, region: "kr" }, function(results, status) {
    if (status === "OK" && results[0]) {
      callback(results[0].geometry.location);
    } else {
      alert("주소 변환 실패: " + status);
    }
  });
}

window.onload = initMap;
