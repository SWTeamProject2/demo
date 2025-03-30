// weather.js
function getOriginWeather(lat, lng) {
  const weatherUrl = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&appid=9e1bc332e154ed7922661aeea798d30e&units=metric`;
  fetch(weatherUrl)
    .then(response => response.json())
    .then(data => {
      document.getElementById("originWeather").innerHTML =
        "출발지 날씨 - 기온: " + data.main.temp + "°C, " +
        "날씨: " + data.weather[0].description;
    })
    .catch(error => {
      console.error("출발지 날씨 API 호출 오류:", error);
      document.getElementById("originWeather").innerHTML = "출발지 날씨 정보를 가져오지 못했습니다.";
    });
}

function getDestinationWeather(lat, lng) {
  const weatherUrl = `https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${lng}&appid=9e1bc332e154ed7922661aeea798d30e&units=metric`;
  fetch(weatherUrl)
    .then(response => response.json())
    .then(data => {
      document.getElementById("destinationWeather").innerHTML =
        "도착지 날씨 - 기온: " + data.main.temp + "°C, " +
        "날씨: " + data.weather[0].description;
    })
    .catch(error => {
      console.error("도착지 날씨 API 호출 오류:", error);
      document.getElementById("destinationWeather").innerHTML = "도착지 날씨 정보를 가져오지 못했습니다.";
    });
}
