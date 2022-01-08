var map;
let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;



function initMap() {

    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    const infoWindow = new google.maps.InfoWindow();

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: centerZoom,
        center: { lat: centerLatitude, lng: centerLongitude },
    });

    directionsRenderer.setMap(map);
    getAutocompleteData();


    const onChangeHandler = function () {
        calculateAndDisplayRoute(directionsService, directionsRenderer);
        getAutocompleteData();
            };

    document.querySelector("#submit-button").addEventListener("click", onChangeHandler);


    let request = {
        query: "'US national park'",
    };


    service = new google.maps.places.PlacesService(map);
    service.textSearch(request, (results, status) => {
        let jsonString = JSON.stringify(results);
        let jsonObject = JSON.parse(jsonString);
        if (status === google.maps.places.PlacesServiceStatus.OK && results) {
            displayMarkerAndInfoWindow(jsonObject);
        }
    });


    function displayMarkerAndInfoWindow(places) {
        for (let i = 0; i < places.length; i++) {
            const marker = new google.maps.Marker({
                map: map,
                position: places[i].geometry.location,
                title: places[i].name,
            });

            let infoWindowDefaultText = "point of interest";
            let infoWindowMarkerText = "<b>"+`${places[i].name}`+"</b>" + "<br>" + `${places[i].formatted_address}` + "<br>" + `User Rating: ${places[i].rating}`;

            marker.addListener("click", () => {
                infoWindow.setContent(infoWindowMarkerText || infoWindowDefaultText);
                infoWindow.open({
                    anchor: marker,
                    map,
                });
            });
        }
    }




    function getAutocompleteData() {

        autocompleteRequest =
        {
            componentRestrictions: {'country': ['us']},
            fields: ['geometry', 'name', 'formatted_address']
        }
        var originInput = document.getElementById("originInput");
        var origin = new google.maps.places.Autocomplete(originInput, autocompleteRequest);
        var destinationInput = document.getElementById("destinationInput");
        var destination = new google.maps.places.Autocomplete(destinationInput, autocompleteRequest);
        let originLatLong = [];
        let destinationLatLong = [];
  origin.addListener("place_changed", () => {
             const originSelected = origin.getPlace();
             const jsonAutocompleteOrigin = JSON.stringify(originSelected);
             const jsonAutoObjectOrigin = JSON.parse(jsonAutocompleteOrigin);
             let originLat = jsonAutoObjectOrigin.geometry.location.lat;
             let originLong = jsonAutoObjectOrigin.geometry.location.lng;
             originLatLong.push(originLat, originLong);
             console.log(originLatLong);
             });
        destination.addListener("place_changed", () => {
             const destinationSelected = destination.getPlace();
             const jsonAutocompleteDestination = JSON.stringify(destinationSelected);
             const jsonAutoObjectDestination = JSON.parse(jsonAutocompleteDestination);
             let destinationLat = jsonAutoObjectDestination.geometry.location.lat;
             let destinationLong = jsonAutoObjectDestination.geometry.location.lng;
             destinationLatLong.push(destinationLat, destinationLong);
             console.log(destinationLatLong);

           });


        google.maps.event.addDomListener(originInput, "keydown", function(event) {
            if (event.keyCode === 13){
                event.preventDefault();
            }
        });

        google.maps.event.addDomListener(destinationInput, "keydown", function(event) {
            if (event.keyCode === 13){
                event.preventDefault();
            }
        });
console.log(originLatLong);
// const polygonCoords = [
//                          {lat: originLatLong[0], lng: originLatLong[1]},
//                          {lat: 40.394358733076, lng: -84.748268289089},
//                          {lat: destinationLatLong[0] + 2, lng: destinationLatLong[1]},
//                          {lat: 39.384308649558, lng: -119.815360985150},
//                          {lat: originLatLong[0], lng: originLatLong[1]}
//                        ];
//                        const polygon = new google.maps.Polygon({
//                            paths: polygonCoords,
//                            strokeColor: "#FF0000",
//                            strokeOpacity: 0.8,
//                            strokeWeight: 2,
//                            fillColor: "#FF0000",
//                            fillOpacity: 0.35,
//                          });
//
//                          polygon.setMap(map);
//
}

function calculateAndDisplayRoute(directionsService, directionsRenderer) {
    var request = {
        origin: document.getElementById("originInput").value,
        destination: document.getElementById("destinationInput").value,
        travelMode: google.maps.TravelMode.DRIVING,
        unitSystem: google.maps.UnitSystem.IMPERIAL
    }
    directionsService.route(request)
    .then((response) => {
        directionsRenderer.setDirections(response);
    })
        .catch((e) => window.alert("Directions request failed due to " + status));
}

}




