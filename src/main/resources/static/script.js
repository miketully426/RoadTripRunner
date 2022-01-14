var map;
let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
let parkUrl = "https://developer.nps.gov/api/v1/parks?limit=50&api_key=ljfsoa6TcSZddUPBiKFw450uW1FKOU0N03N6Tsux";


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
    };

    document.querySelector("#submit-button").addEventListener("click", onChangeHandler);

    let request = {
        query: "'US national park'",
    };

    let nationalParks [];
    service = new google.maps.places.PlacesService(map);
    service.textSearch(request, (results, status) => {
        let jsonString = JSON.stringify(results);
        let jsonObject = JSON.parse(jsonString);
        if (status === google.maps.places.PlacesServiceStatus.OK && results) {
            nationalParks = displayMarkerAndInfoWindow(jsonObject);
        }
    });


    function displayMarkerAndInfoWindow(places) {
        let markers = [];
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
            findPointsOfInterest(response.routes[0].overview_polyline, map, nationalParks);
        });
        .catch((e) => window.alert("Sorry, we could not calculate driving directions for these locations. Please try a different location."));
    }

}

//
//function findPointsOfInterest (encodedWaypoints, map, nationalParks) {
//
//    let decodedWaypoints = decode(encodedWaypoints);
//    let waypoints = [];
//
//    for (let i = 0; i < decodedWaypoints.length; i+=15) {
//        waypoints.push(decodedWaypoints[i]);
//    }
//
//    let allCircles = [];
//    for(const waypoint of waypoints) {
//        var waypointLatLng = new google.maps.LatLng(waypoint[0], waypoint[1])
//        var waypointCircle = new google.maps.Circle({
//            strokeColor: "#add8e6",
//                strokeOpacity: 0,
//                strokeWeight: 0,
//                fillColor: "#add8e6",
//                fillOpacity: 0.35,
//                map,
//                center: waypointLatLng,
//                radius: 160000,
//        });
//
//        allCircles.push(waypointCircle);
//    }
//
//
//    let parksInCircles = [];
//
//    for (park of nationalParks) {
//    let withinBounds = false;
//        for (waypointCircle of allCircles) {
//            if (google.maps.geometry.spherical.computeDistanceBetween(park.getPosition(), waypointCircle.getCenter()) <= waypointCircle.getRadius()) {
//                console.log('=> is in searchArea');
//                withinBounds = true;
//            }
//            else {
//                console.log('=> is NOT in searchArea');
//            }
//        }
//        if (withinBounds == false){
//            console.log("removing park")
//            park.setMap(null);
//        }
//    }
//
//}//end of findPointsOfInterest
//
//function nationalParksRequest(parkUrl){
//    var myHeaders = new Headers();
//    myHeaders.append("x-api-key", "ljfsoa6TcSZddUPBiKFw450uW1FKOU0N03N6Tsux");
//
//    var requestOptions = {
//    method: 'GET',
//    headers: myHeaders,
//    redirect: 'follow'
//    };
//
//    fetch("https://developer.nps.gov/api/v1/parks?limit=465&api_key=ljfsoa6TcSZddUPBiKFw450uW1FKOU0N03N6Tsux", requestOptions)
//    .then(response => response.text())
//    .then(result => console.log(result))
//    .catch(error => console.log('error', error))
//}