let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
let displayUSA;
var map;

// displays center of US
// zoom of 5



function initMap() {
//  const directionsService = new google.maps.DirectionsService();
//  const directionsRenderer = new google.maps.DirectionsRenderer();
//  displayUSA = new google.maps.LtLng(37.85, -97);
//  const infoWindow = new google.maps.InfoWindow();
    const map = new google.maps.Map(document.getElementById("map"), {
    zoom: centerZoom,
    center: { lat: centerLatitude, lng: centerLongitude },
  });

//  directionsRenderer.setMap(map);

//  const onChangeHandler = function () {
//    calculateAndDisplayRoute(directionsService, directionsRenderer);
//  };
//
//  document.getElementById("start").addEventListener("change", onChangeHandler);
//  document.getElementById("end").addEventListener("change", onChangeHandler);

//  http request searching for querytext of 'National Parks' and will return just the name
  let request = {
//    input: "national park"
    query: "'us national park'",
    fields: ["name", "geometry.location"]

    //either input OR query + fields works.
  };

   const placesService = new google.maps.places.PlacesService(map);

//created jsonObject inside and outside of
   service = new google.maps.places.PlacesService(map);
//   service.findPlaceFromQuery
   service.textSearch(request, (results, status) => {
     let jsonString = JSON.stringify(results);
     let jsonObject = JSON.parse(jsonString);
     console.log(jsonObject[0]);
     if (status === google.maps.places.PlacesServiceStatus.OK && results) {
       for (let i = 0; i < jsonObject.length; i++) {
           const marker = new google.maps.Marker({
             map: map,
             position: jsonObject[i].geometry.location,
           });
        //call window function to display query
       }
//       map.setCenter(jsonObject[0].geometry.location);
     }
   });
}


function createMarker(place) {
  if (!place.geometry || !place.geometry.location) {
    return;
  }

  const marker = new google.maps.Marker({
    map: map,
    position: place.geometry.location,
  });

   google.maps.event.addListener(marker, "click", () => {
     infoWindow.setContent(place.name || "");
     infoWindow.open(map);
     });
}


//function calculateAndDisplayRoute(directionsService, directionsRenderer) {
//  directionsService
//    .route({
//      origin: {
//        query: document.getElementById("start").value,
//      },
//      destination: {
//        query: document.getElementById("end").value,
//      },
//      travelMode: google.maps.TravelMode.DRIVING,
//    })
//    .then((response) => {
//      directionsRenderer.setDirections(response);
//    })
//    .catch((e) => window.alert("Directions request failed due to " + status));
//}

