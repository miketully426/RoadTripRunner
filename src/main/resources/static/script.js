var map;
let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
//var originInput; //element for the dom (what user types in startingLocation)
//var origin; //autocomplete object for originInput
//var destinationInput;
//var destination;

//look into creating function for collecting autocomplete data
function getAutocompleteData() {
 let autocompleteRequest =
  {
   componentRestrictions: {'country': ['us']},
   fields: ['geometry', 'name', 'formatted_address']
  }

   var originInput = document.getElementById("originInput");
   var origin = new google.maps.places.Autocomplete(originInput, autocompleteRequest);
   var destinationInput = document.getElementById("destinationInput");
   var destination = new google.maps.places.Autocomplete(destinationInput, autocompleteRequest);
   var originPlace;

   //declaring origin as an extension of the MVC Object class. may not be necessary
   origin.prototype = new google.maps.MVCObject();


//   google.maps.event.addListener(origin, "place_changed", () => {
//     var originPlace = origin.getPlace();
//   });


// .addListener is a MVCObject method. It takes the event "place_changed", which is the
// only option for the autocomplete object. (autocomplete extends MVCObject)
   origin.addListener("place_changed", () => {
     let originSelected = origin.getPlace();
     let jsonAutocomplete = JSON.stringify(originSelected);
     let jsonAutoObject = JSON.parse(jsonAutocomplete);
     let name = origin.get(jsonAutoObject.name);
     window.alert("The name is " + name + "!")
   });

//   origin.addDomListener(originInput, "click", () => {
//     window.alert("Input has been changed!");
//   });

//     originPlace = origin.getPlace();
//     let jsonAutocomplete = JSON.stringify(originPlace);
//     let jsonAutoObject = JSON.parse(jsonAutocomplete);
//   console.log(origin);
//   console.log(jsonAutoObject);
}





function initMap() {

//
//   let jsonAutocomplete = JSON.stringify(originPlace);
//   console.log(jsonAutocomplete);
//   let jsonAutoObject = JSON.parse(jsonAutocomplete);
//
//   if (jsonAutocomplete === "undefined") {
//     console.log("OOPS!");
//   }
//
//   if (jsonAutoObject === "undefined") {
//     console.log("parsed isn't working");
//   }


  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: centerZoom,
    center: { lat: centerLatitude, lng: centerLongitude },
  });


  const directionsService = new google.maps.DirectionsService();
  const directionsRenderer = new google.maps.DirectionsRenderer();
  const infoWindow = new google.maps.InfoWindow();


//  directionsRenderer.setMap(map);
//
//  const onChangeHandler = function () {
//    calculateAndDisplayRoute(directionsService, directionsRenderer);
//  };
//
//  originInput.addEventListener("change", onChangeHandler);
//  destinationInput.addEventListener("change", onChangeHandler);

  let request = {
    query: "'US national park'",
  };

   const placesService = new google.maps.places.PlacesService(map);


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
}



function autocompleteInput() {
   var originInput = document.getElementById("originInput");
   var origin = new google.maps.places.Autocomplete(originInput,
   {
     componentRestrictions: {'country': ['us']},
     fields: ['geometry', 'name', 'formatted_address']
   });
   var destinationInput = document.getElementById("destinationInput");
   var destination = new google.maps.places.Autocomplete(destinationInput,
   {
    componentRestrictions: {'country': ['us']},
    fields: ['geometry', 'name', 'formatted_address']
   });
}




//function calculateAndDisplayRoute(directionsService, directionsRenderer) {
//  directionsService
//    .route({
//      origin: {
//        query: originInput.value,
//      },
//      destination: {
//        query: originInput.value,
//      },
//      travelMode: google.maps.TravelMode.DRIVING,
//    })
//    .then((response) => {
//      directionsRenderer.setDirections(response);
//    })
//    .catch((e) => window.alert("Directions request failed due to " + status));
//}
