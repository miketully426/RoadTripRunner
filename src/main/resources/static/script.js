let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
let displayUSA;
var map;

// displays center of US
// zoom of 5



function initMap() {
  const directionsService = new google.maps.DirectionsService();
  const directionsRenderer = new google.maps.DirectionsRenderer();
//  displayUSA = new google.maps.LtLng(37.85, -97);
  const infoWindow = new google.maps.InfoWindow();
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

/*NOTES:
1. From Emily: either input OR query + fields works. From Krystal: I didn't get the input feature to work with the info window.
2. the search text needs to be inside double quotes AND single quotes so that it searches for the exact phrase
Will we want the request to be a user selected query or will it only be national parks?
*/
//  http request searching for querytext of 'National Parks' and will return just the name
  let request = {
//    input: "national park"
    query: "'us national park'",
    fields: ["name", "geometry.location"]
  };

   const placesService = new google.maps.places.PlacesService(map);

/*NOTES:
1. Tried creating createMarker function that takes in JSONObject as parameter and then loops through the jsonObject array to place markers.
This didn't work; it loaded the map but had no markers within it. Also got two "user denied access to database" errors.
However, the console.log did show that the JSON is being passed into the console, so the error is in the code rendering.

2.  ??? Try MarkerClusterer to attempt more marker placement???
https://stackoverflow.com/questions/3059044/google-maps-js-api-v3-simple-multiple-marker-example

3. SUCCESS! Connected marker with json.name text when looping through and placing marker initially as part of init() function.
*/

//created jsonObject inside and outside of
   service = new google.maps.places.PlacesService(map);
//   service.findPlaceFromQuery(request, (results, status) => {
   service.textSearch(request, (results, status) => {
     let jsonString = JSON.stringify(results);
     let jsonObject = JSON.parse(jsonString);
     if (status === google.maps.places.PlacesServiceStatus.OK && results) {
        console.log(jsonObject[0]);  // use to see what output comes from API

//        createMarker(jsonObject);
       for (let i = 0; i < jsonObject.length; i++) {
           const marker = new google.maps.Marker({
             map: map,
             position: jsonObject[i].geometry.location,
             title: jsonObject[i].name,
           });

           let infoWindowDisplayText = "<h1>National Park</h1>";

           marker.addListener("click", () => {
                infoWindow.setContent(jsonObject[i].name || infoWindowDisplayText);
                infoWindow.open({
                    anchor: marker,
                    map });
                });
           }
       }
   });
}

function createMarker(json) {

//  const marker = new google.maps.Marker({
//    map: map,
//    position: place.geometry.location,
//  });
    for (let i = 0; i < json.length; i++) {
        const marker = new google.maps.Marker({
             map: map,
             position: json[i].geometry.location,
             title: json[i].name,
        });

        let infoWindowDisplayText = "<h1>National Park</h1>";

        marker.addListener("click", () => {
            infoWindow.setContent(json[i].name || infoWindowDisplayText);
                infoWindow.open({
                    anchor: marker,
                    map });
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

