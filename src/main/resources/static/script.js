var map;
var originSelected;
var destinationSelected;
var jsonAutocompleteOrigin;
var jsonAutoObjectOrigin;
var jsonAutocompleteDestination;
var jsonAutoObjectDestination;
let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;



//
//function getAutocompleteData() {

    //might eventually want to add placeId as a field... TBD
//    let autocompleteRequest =
//    {
//        componentRestrictions: {'country': ['us']},
//        fields: ['geometry', 'name', 'formatted_address']
//    }
//
//    var originInput = document.getElementById("originInput");
//    var origin = new google.maps.places.Autocomplete(originInput, autocompleteRequest);
//    var destinationInput = document.getElementById("destinationInput");
//    var destination = new google.maps.places.Autocomplete(destinationInput, autocompleteRequest);
//    var originPlace;
//    var jsonAutocompleteOrigin;
//    var jsonAutoObjectOrigin;
//    var jsonAutocompleteDestination;
//    var jsonAutoObjectDestination;


    //declaring origin as an extension of the MVC Object class. likely unnecessary, TEST!
//    origin.prototype = new google.maps.MVCObject();



    /*  - .addListener is a MVCObject method. It takes the event "place_changed", which is the
            only option for the autocomplete object. (autocomplete extends MVCObject)
    - will need to add logic in here to bind jsonobject to model if user is logged in  */

//    origin.addListener("place_changed", () => {
//    const originSelected = origin.getPlace();
//
//    if (!originSelected.name || !originSelected.geometry) {
//        window.alert("Yikes! We can't process that location. Please try another");
//    }
//
//    jsonAutocompleteOrigin = JSON.stringify(originSelected);
//    jsonAutoObjectOrigin = JSON.parse(jsonAutocompleteOrigin);



//        // HTTP REQUEST (I THINK THIS MIGHT BE AJAX TOO...)
//        function sendJSON() {
//            let originRequest = new XMLHttpRequest();
//            originRequest.open("POST", localhost8080/??, true);
//            originRequest.setRequestHeader("Content-Type", "application/json");
//
//     //create state change callback?
//            originRequest.onreadystatechange = function () {
//                if (originRequest.readyState === 4 && originRequest.status === 200) {
//
//            //print received data from server
//                    result.innerHTML = this.responseText;
//                }
//            };
//
//             //convert JSON data to string
//            var data = JSON.stringify({ /*object here*/ });
//
//            //send data with request
//            originRequest.send(data);
//
//            //use post because we want to send to database and save it
//            // include url localhost8080/api ... (will not be a view)



        //USING AJAX
        //     $.ajax({
        //       type : "POST",
        //       url : "/maps/directions",
        //       contentType: "application/json",
        //       data: jsonAutoObjectOrigin
        //     });



//    console.log(jsonAutoObjectOrigin) //eventually take this out
//
//    });
//
//    destination.addListener("place_changed", () => {
//        const destinationSelected = destination.getPlace();
//
//        if (!destinationSelected.name || !destinationSelected.geometry) {
//            window.alert("Yikes! We can't process that location. Please try another.");
//        }
//
//        const jsonAutocompleteDestination = JSON.stringify(destinationSelected);
//        const jsonAutoObjectDestination = JSON.parse(jsonAutocompleteDestination);
//        console.log(jsonAutoObjectDestination); //eventually take this out
//    });

//}



function initMap() {

   getAutocompleteData();


    const directionsService = new google.maps.DirectionsService();
    const directionsRenderer = new google.maps.DirectionsRenderer();
    const infoWindow = new google.maps.InfoWindow();

    const map = new google.maps.Map(document.getElementById("map"), {
        zoom: centerZoom,
        center: { lat: centerLatitude, lng: centerLongitude },
    });


    directionsRenderer.setMap(map);

    calculateAndDisplayRoute(directionsService, directionsRenderer, callback);

//    const onChangeHandler = function () {
//        calculateAndDisplayRoute(directionsService, directionsRenderer);
//    };
//
//    originSelected.addEventListener("change", onChangeHandler);
//    destinationSelected.addEventListener("change", onChangeHandler);


    let request = {
        query: "'US national park'",
    };

    //   const placesService = new google.maps.places.PlacesService(map);


    service = new google.maps.places.PlacesService(map);
    service.textSearch(request, (results, status) => {
        let jsonString = JSON.stringify(results);
        let jsonObject = JSON.parse(jsonString);
        if (status === google.maps.places.PlacesServiceStatus.OK && results) {
            console.log(jsonObject[10]);

            for (let i = 0; i < jsonObject.length; i++) {
                const marker = new google.maps.Marker({
                    map: map,
                    position: jsonObject[i].geometry.location,
                    title: jsonObject[i].name,
                });

                let infoWindowDefaultText = "National Park";
                let infoWindowMarkerText = "<b>"+`${jsonObject[i].name}`+"</b>" + "<br>" + `${jsonObject[i].formatted_address}` + "<br>" + `User Rating: ${jsonObject[i].rating}`;

                marker.addListener("click", () => {
                    infoWindow.setContent(infoWindowMarkerText || infoWindowDefaultText);
                    infoWindow.open({
                        anchor: marker,
                        map });
                });
            }
        }
    });

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
    var originPlace;




    origin.addListener("place_changed", () => {
        const originSelected = origin.getPlace();

        if (!originSelected.name || !originSelected.geometry) {
            window.alert("Yikes! We can't process that location. Please try another");
        }
//
////        originSelected.addEventListener("change", onChangeHandler);
//
        jsonAutocompleteOrigin = JSON.stringify(originSelected);
        jsonAutoObjectOrigin = JSON.parse(jsonAutocompleteOrigin);
//
//        console.log(jsonAutoObjectOrigin) //eventually take this out

    });

    destination.addListener("place_changed", () => {
        const destinationSelected = destination.getPlace();

        if (!destinationSelected.name || !destinationSelected.geometry) {
            window.alert("Yikes! We can't process that location. Please try another.");
        }

//        const jsonAutocompleteDestination = JSON.stringify(destinationSelected);
//        const jsonAutoObjectDestination = JSON.parse(jsonAutocompleteDestination);
//        console.log(jsonAutoObjectDestination); //eventually take this out
    });




}

    function calculateAndDisplayRoute(directionsService, directionsRenderer, callback) {
        directionsService
        .route({
            origin: jsonAutoObjectOrigin.geometry.location,
            destination: jsonAutoObjectDestination.geometry.location,
            travelMode: google.maps.TravelMode.DRIVING,
        }), function (results, status) {
            if (google.maps.places.PlacesServiceStatus.OK == "OK") {
                callback(results);
            }
            else {
            alert("Fetching place was unsuccessful due to " + status);
            }
        }
        .then((response) => {
            directionsRenderer.setDirections(response);
        })
       .catch((e) => window.alert("Directions request failed due to " + status));
    }
}




