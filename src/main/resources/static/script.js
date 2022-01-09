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
            findPointsOfInterest(response.routes[0].overview_polyline, map);
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
               strokeColor: "#000000",
               strokeOpacity: 0.1,
               strokeWeight: 1,
               fillColor: "#000000",
               fillOpacity: 0.1,
             });

             polygon.setMap(map);

        })
            .catch((e) => window.alert("Boundary box failed"));

    }
}  //end of InitMap

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
    .catch((e) => window.alert("Sorry, we could not calculate driving directions for these locations. Please try a different location."));
}

function findPointsOfInterest (encodedWaypoints, map) {

    let decodedWaypoints = decode(encodedWaypoints);
    let waypoints = []

    for (let i = 0; i < decodedWaypoints.length; i+=30){
      waypoints.push(decodedWaypoints[i]);
    }
    console.log(map);
    for(const waypoint of waypoints) {
      console.log(waypoint);
      let waypointLatLng = { lat: waypoint[0], lng: waypoint[1]}
      let waypointCircle = new google.maps.Circle({
         strokeColor: "#FF0000",
              strokeOpacity: 0.8,
              strokeWeight: 2,
              fillColor: "#FF0000",
              fillOpacity: 0.35,
              map: map,
              center: waypointLatLng,
              radius: Math.sqrt(waypointLatLng) * 160000,
      });
    }

    }
    //for loop for every 30 waypoints put the next waypoint into an array
    //array will have approx 10 points
    //for loop that says for each waypoint use that as the center of the circle.  The radius is the distance to the next waypoint
