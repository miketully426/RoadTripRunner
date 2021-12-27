var map;
let centerLatitude = 41.85;
let centerLongitude = -87.65;
let centerZoom = 7;
let autocomplete;

function initMap() {
  const directionsService = new google.maps.DirectionsService();
  const directionsRenderer = new google.maps.DirectionsRenderer();
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

  const onPlaceChanged = function (){
    initStartAndEndDestinationAutocomplete ();
  }

  document.getElementById("start_autocomplete").addEventListener("placeChanged", onPlaceChanged);
  document.getElementById("end_autocomplete").addEventListener("placeChanged", onPlaceChanged)
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


//function initStartAndEndDestinationAutocomplete() {
//    //everytime you autocomplete some thing you get a place object
//    //takes in two parameters- 1.  id element from form  2.  object that takes options for autocomplete
//    autocomplete = new google.maps.places.Autocomplete (
//        document.getElementById('autocomplete'),
//        {
//            types: ['national park', 'address'],
//            //component restrictions restricts search to a specific  country and makes search easier
//            componentRestrictions: { 'country' : ['us']},
//            //geometry- geolocation of your house, properly fomatted address, etc.
//            fields: ['place_id', 'geometry', 'name']
//        });

//
//}

var options = {
    types: ['(cities']
}

var input1 = document.getElementByID("start_autocomplete");
var autocomplete1 = new google.maps.places.Autocomplete(input1, options);

var input2 = document.getElementByID("end_autocomplete");
var autocomplete2 = new google.maps.places.Autocomplete(input2, options);