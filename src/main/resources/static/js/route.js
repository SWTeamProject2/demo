// route.js
const modeColors = {
  DRIVING: "#FF0000",
  WALKING: "#008000",
  BICYCLING: "#0000FF",
  TRANSIT: "#800080"
};

function findRoutes(origin, destination) {
  // 기존 경로 정보 초기화
  document.getElementById("routeInfo").innerHTML = "";

  const travelModes = ["driving", "walking", "transit", "bicycling"];
  let foundAny = false;
  let bounds = new google.maps.LatLngBounds();

  travelModes.forEach(function(mode) {
    directionsService.route({
      origin: origin,
      destination: destination,
      travelMode: google.maps.TravelMode[mode.toUpperCase()]
    }, function(result, status) {
      if (status === "OK") {
        foundAny = true;
        const renderer = new google.maps.DirectionsRenderer({
          map: map,
          preserveViewport: true,
          suppressMarkers: true,
          polylineOptions: { strokeColor: modeColors[mode.toUpperCase()], strokeWeight: 5 }
        });
        renderer.setDirections(result);
        bounds.union(result.routes[0].bounds);
        map.fitBounds(bounds);

        const leg = result.routes[0].legs[0];
        document.getElementById("routeInfo").innerHTML +=
          mode.toUpperCase() + " 경로: 거리 " + leg.distance.text + ", 소요시간 " + leg.duration.text + "<br>";
      } else {
        console.warn(mode.toUpperCase() + " 경로 요청 실패: " + status);
      }
    });
  });

  setTimeout(function() {
    if (!foundAny) {
      document.getElementById("routeInfo").innerText = "가능한 경로가 없습니다.";
    }
  }, 1000);
}
