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
    };

    document.querySelector("#submit-button").addEventListener("click", onChangeHandler);

    let request = {
        query: "'US national park'",
    };


    service = new google.maps.places.PlacesService(map);
    service.textSearch(request, (results, status) => {
        let jsonObject = JSON.parse(JSON.stringify(results));
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

/* can call directionsRenderer from Java side
public static DirectionsApiRequest getDirections(
      GeoApiContext context, String origin, String destination) {
    return new DirectionsApiRequest(context).origin(origin).destination(destination);
  }
*/


function sendPostRequest(jsonLocationObject) {
	request.open(“POST”, "http://localhost:8080/planATrip/geocode", true);
	request.setRequestHeader("Content-Type”, “application/json; charset=UTF-8”);
	request.send(JSON.parse(JSON.stringify(jsonLocationObject));

	return;
}
