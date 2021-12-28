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
//  const startInput = document.getElementById("start_autocomplete");
//  const endInput = document.getElementById("end_autocomplete");
//  const submit = document.getElementById("submit");
//  const startAutocomplete = new google.maps.places.Autocomplete(startInput);
//
//  startAutocomplete.setFields(["place_id"]);
//
//  const
//
//
//  new AutocompleteDirectionsHandler(map)

  directionsRenderer.setMap(map);

  const onChangeHandler = function () {
    calculateAndDisplayRoute(directionsService, directionsRenderer);
  };

  document.getElementById("start").addEventListener("change", onChangeHandler);
  document.getElementById("end").addEventListener("change", onChangeHandler);

//  const initAutocomplete = function () {
//    initAutocomplete();
//  };

  const onPlaceChanged = function (){
    initStartAndEndDestinationAutocomplete ();

   document.getElementById("start_autocomplete").addEventListener("place_changed", onPlaceChanged);
   document.getElementById("end_autocomplete").addEventListener("place_changed", onPlaceChanged);
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


function initStartAndEndDestinationAutocomplete() {
    //everytime you autocomplete some thing you get a place object
    //takes in two parameters- 1.  id element from form  2.  object that takes options for autocomplete
    autocomplete = new google.maps.places.Autocomplete (
        document.getElementById('autocomplete'),
        {
            types: ['national park', 'address'],
            //component restrictions restricts search to a specific  country and makes search easier
            componentRestrictions: { 'country' : ['us']},
            //geometry- geolocation of your house, properly fomatted address, etc.
            fields: ['place_id', 'geometry', 'name']
        });
    autocomplete.addEventListener("place_changed", () => {
        const place = autocomplete.getPlace();
        });
}
//function initAutocomplete(){
//    var options = {
//        types: ['(cities']
//    }
//
//    var input1 = document.getElementById("start_autocomplete");
//    var autocomplete1 = new google.maps.places.Autocomplete(input1, options);
//
//    var input2 = document.getElementById("end_autocomplete");
//    var autocomplete2 = new google.maps.places.Autocomplete(input2, options);
//}

