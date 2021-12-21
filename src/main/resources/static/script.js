let centerLatitude = 41.85;
let centerLongitude = -87.65;
let centerZoom = 7;

let trialAttemptsLatitude = [41, 60];
let trialAttemptsLongitude = [-87, -87.5];

function initMap() {
  const directionsService = new google.maps.DirectionsService();
  const directionsRenderer = new google.maps.DirectionsRenderer();
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: centerZoom,
    center: { lat: centerLatitude, lng: centerLongitude },
  });

  directionsRenderer.setMap(map);

  const onChangeHandler = function () {
    calculateAndDisplayRoute(directionsService, directionsRenderer);
  };

  document.getElementById("start").addEventListener("change", onChangeHandler);
  document.getElementById("end").addEventListener("change", onChangeHandler);

//attempts to see whether we could put down markers for various lat/longs in a loop
  for (let i = 0; i < trialAttemptsLatitude.length; i++) {
    const marker = new google.maps.Marker({
        position: { lat: trialAttemptsLatitude[i], lng: trialAttemptsLongitude[i] },
        map: map,
      });
  }

}

function calculateAndDisplayRoute(directionsService, directionsRenderer) {
  directionsService
    .route({
      origin: {
        query: document.getElementById("start").value,
      },
      destination: {
        query: document.getElementById("end").value,
      },
      travelMode: google.maps.TravelMode.DRIVING,
    })
    .then((response) => {
      directionsRenderer.setDirections(response);
    })
    .catch((e) => window.alert("Directions request failed due to " + status));
}

//
//var request = require("request");
//const rp = require('request-promise');


//fetch('developer.nps.gov/api/v1')
//  .then((response) => response.json())
//  .then((json) => console.log(json));
//
//
//var options = {
//    method : 'GET',
//    url : 'https://developer.nps.gov/api/v1/parks',
//    qs: { parkCode: 'zion'},
//    headers: {
//        'User-Agent': 'Request-Promise',
//        'cache-control': 'no-cache',
//        'Authorization': '${natParksApiKey}'
//        },
//    json: true
// };
//
//
//
//fetch(options)
//    .then(function(responseData) {
//    console.log(responseData);
//    })
//    .catch(function(err) {
//    console.log("error");
//    });
//
