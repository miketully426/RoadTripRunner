let centerLatitude = 41.85;
let centerLongitude = -87.65;
let centerZoom = 7;

let trialAttemptsLatitude = [41, 40];
let trialAttemptsLongitude = [-87, -87];

function initMap() {
    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();

    const infoWindow = new google.maps.InfoWindow(); //needs parameter of content: contentString to have information to display for a marker

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: centerZoom,
        center: { lat: centerLatitude, lng: centerLongitude },
    });

    directionsRenderer.setMap(map);  //.setMap(map) is causing issues with the Places API pull

    const onChangeHandler = function () {
        calculateAndDisplayRoute(directionsService, directionsRenderer);
    };

    document.getElementById("start").addEventListener("change", onChangeHandler);
    document.getElementById("end").addEventListener("change", onChangeHandler);

//attempts to see whether we could put down markers for various lat/longs in a loop --> should we be pulling a json file and placing in the mapDisplay.html file?
    for (let i = 0; i < trialAttemptsLatitude.length; i++) {
        const marker = new google.maps.Marker({
            position: { lat: trialAttemptsLatitude[i], lng: trialAttemptsLongitude[i] },
            map: map,
        });
    }

  //  http request searching for querytext of 'National Parks' and will return just the name
//  query text searches return all fields; if want only limited fields, use "find place" and input search instead
    let request = {
        input: "amusement_park", //using input and amusement_park returns the json listing; setMap is blocking it
//        query: "National Park", //should this be input and park?
        fields: ["name", "geometry"] //should this be geometry.location for query search and name, geometry for input search
//        json field return geometry:location:lat lng
    };

    const placesService = new google.maps.places.PlacesService(map);

    service = new google.maps.places.PlacesService(map);
    service.textSearch(request, (results, status) => {
//    service.findPlaceFromQuery(request, (results, status) => {
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
        position: place.geometry.location,
        map: map,
    });

/*
marker.addListener("click", () => {
    infowindow.open({
      anchor: marker,
      map,
      shouldFocus: false,
    });
    */


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