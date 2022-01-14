var map;
let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
let natParkData;

let parkUrl = "https://developer.nps.gov/api/v1/parks?limit=465&api_key=ljfsoa6TcSZddUPBiKFw450uW1FKOU0N03N6Tsux";

async function getNationalParkData() {
    let response = await fetch(parkUrl);
    natParkData = await response.json();
    console.log(natParkData.data[0].fullName);
}

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
    getNationalParkData();
//    displayMarkerAndInfoWindow(natParkData);

    const onChangeHandler = function () {
        calculateAndDisplayRoute(directionsService, directionsRenderer);
    };

    document.querySelector("#submit-button").addEventListener("click", onChangeHandler);

//    let request = {
//        query: "'US national park'",
//    };
//
    let nationalParks = [];
//    service = new google.maps.places.PlacesService(map);
//    service.textSearch(request, (results, status) => {
////        let jsonString = JSON.stringify(results);
////        let jsonObject = JSON.parse(jsonString);
//        if (status === google.maps.places.PlacesServiceStatus.OK && results) {
//            //&& if it is in the circles
//            nationalParks = displayMarkerAndInfoWindow(jsonObject);
//        }
//    });

    function displayMarkerAndInfoWindow(places) {
        let markers = []
        for (let i = 0; i < places.length; i++) {
            const marker = new google.maps.Marker({
                map: map,
                position: places[i].geometry.location,
                title: places[i].name,
            });
            markers.push(marker);
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
        return markers;
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
                findPointsOfInterest(response.routes[0].overview_polyline, map, nationalParks)
            })
           .catch((e) => {
                console.log(e);
                window.alert("Sorry, we could not calculate driving directions for these locations. Please try a different location.");
           })
        }
}

function findPointsOfInterest (encodedWaypoints, map, nationalParks) {

    let decodedWaypoints = decode(encodedWaypoints);
    let waypoints = []

    for (let i = 0; i < decodedWaypoints.length; i+=15){
      waypoints.push(decodedWaypoints[i]);
    }
    let allCircles = []
    for(const waypoint of waypoints) {
      var waypointLatLng = new google.maps.LatLng(waypoint[0], waypoint[1]) //gets lat long of waypoints
      var waypointCircle = new google.maps.Circle({ //sets the waypoint center
         strokeColor: "#add8e6",
              strokeOpacity: 0,
              strokeWeight: 0,
              fillColor: "#add8e6",
              fillOpacity: 0.35,
              map,
              center: waypointLatLng,
              radius: 160000, //100 miles
      });
      allCircles.push(waypointCircle); //all circles pushed into allcircles array
     }

     let parksInCircles = [];

     for (park of nationalParks) {
        let withinBounds = false;
         for (waypointCircle of allCircles){ //if the distance between the park's lat/long and the circle's center is less than the circle's radius then the park is in the circle
//         .getPosition is a googleMaps method call that returns LatLng object
//we will need something different to get position of national park when using the natParkAPI
            if (google.maps.geometry.spherical.computeDistanceBetween(park.getPosition(), waypointCircle.getCenter()) <= waypointCircle.getRadius()) {
                withinBounds = true;
//                NEW CODE: if withinBounds = true then setMarker
            }
         }
         if(withinBounds == false){
         park.setMap(null);
         }
     }

}
