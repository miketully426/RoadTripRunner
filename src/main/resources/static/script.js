let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 5;
let displayUSA;

function initMap() {
  const directionsService = new google.maps.DirectionsService();
  const directionsRenderer = new google.maps.DirectionsRenderer();
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

//  http request searching for querytext of 'National Parks' and will return just the name
  let request = {
    query: "National Parks",
    fields: ["name", "geometry"]
  };

   const placesService = new google.maps.places.PlacesService(map);

   service = new google.maps.places.PlacesService(map);
   service.findPlaceFromQuery(request, (results, status) => {
     if (status === google.maps.places.PlacesServiceStatus.OK && results) {
       for (let i = 0; i < results.length; i++) {
         createMarker(results[i]);
       }
       map.setCenter(results[0].geometry.location);
     }
   });
}


function createMarker(place) {
  if (!place.geometry || !place.geometry.location) return;

  const marker = new google.maps.Marker({
    map: map,
    position: place.geometry.location,
  });

  google.maps.event.addListener(marker, "click", () => {
    infoWindow.setContent(place.name || "");
    infoWindow.open(map);
  });
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

