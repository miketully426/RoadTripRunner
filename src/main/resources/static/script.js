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
        calculateAndDisplayRouteAndBoundary(directionsService, directionsRenderer);
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
}

    function calculateAndDisplayRouteAndBoundary(directionsService, directionsRenderer) {
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

    directionsService.route(request)
       .then((response) => {
           directionsRenderer.setDirections(response);
           let originLat = (response.routes[0].legs[0].end_location.lat());
           let originLong = (response.routes[0].legs[0].end_location.lng());
           let destinationLat = (response.routes[0].legs[0].start_location.lat());
           let destinationLong = (response.routes[0].legs[0].start_location.lng());
           console.log(originLat + 5);
           const polygonCoords = [
                                    {lat: originLat + 2, lng: originLong + 4},
                                    {lat: originLat - 2, lng: originLong - 4},
                                    {lat: destinationLat - 2, lng: destinationLong - 4},
                                    {lat: destinationLat + 2, lng: destinationLong + 4},
                                    {lat: originLat + 2, lng: originLong + 4}
                                 ];

           const polygon = new google.maps.Polygon({
               paths: polygonCoords,
               strokeColor: "#FF0000",
               strokeOpacity: 0.8,
               strokeWeight: 2,
               fillColor: "#FF0000",
               fillOpacity: 0.35,
             });

             polygon.setMap(map);

        })
            .catch((e) => window.alert("Boundary box failed"));

    }
}




