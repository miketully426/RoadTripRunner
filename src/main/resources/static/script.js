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
        drawPolygon();
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
        var starting = new google.maps.places.Autocomplete(originInput, autocompleteRequest);

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

          console.log(destination.geometry.location.lng);
          console.log(destination.geometry.location.lat);
    }


<<<<<<< HEAD
 function drawPolygon(){

//         let latOrigin = destinationValue.geometry.location.lat();
//         let jsonOrigin = JSON.stringify(latOrigin);
//         let originObject = JSON.parse(jsonOrigin);
         console.log(starting);


//    const polygonCoords = [
//                  {lat: 39.384308649558, lng: -119.815360985150},
//                  {lat: 46.394348251329, lng: -119.467474821662},
//                  {lat: 30.396318323523, lng: -121.597960374905},
//                  {lat: 40.394358733076, lng: -84.748268289089},
//                  {lat: 39.384308649558, lng: -119.815360985150},
//                ];
//                const polygon = new google.maps.Polygon({
//                    paths: polygonCoords,
//                    strokeColor: "#FF0000",
//                    strokeOpacity: 0.8,
//                    strokeWeight: 2,
//                    fillColor: "#FF0000",
//                    fillOpacity: 0.35,
//                  });
//
//                  polygon.setMap(map);
                   }


}
=======

} //end of InitMap
>>>>>>> 81a5085f07b00ef4fc16fd95a3b98b7591531d97


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




