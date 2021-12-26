let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
var map;


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

  let request = {
    query: "'US national park'",
  };

   const placesService = new google.maps.places.PlacesService(map);

   service = new google.maps.places.PlacesService(map);
   service.textSearch(request, (results, status) => {
     let jsonString = JSON.stringify(results);
     let jsonObject = JSON.parse(jsonString);
     if (status === google.maps.places.PlacesServiceStatus.OK && results) {

       for (let i = 0; i < jsonObject.length; i++) {
           const marker = new google.maps.Marker({
             map: map,
             position: jsonObject[i].geometry.location,
             title: jsonObject[i].name,
           });

           let infoWindowDisplayText = "National Park";

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

