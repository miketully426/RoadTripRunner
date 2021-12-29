
var map;
let centerLatitude = 37.85;
let centerLongitude = -97.65;
let centerZoom = 4;
//var originInput; //element for the dom (what user types in startingLocation)
//var origin; //autocomplete object for originInput
//var destinationInput;
//var destination;

function initMap() {
   //autocomplete creation below. could this be a function that is called first in initmap? to do so, would need to declare variables at top?
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
    fields: ['geometry', 'name', 'formatted_address'] //is there anything else we need? we'll want to create a location object with this info
   });
   //need to handle when a user hits enter upon finding selected place
   //event listener for when a place is selected? it doesn't seem needed
   //event listener for button click


  const directionsService = new google.maps.DirectionsService();
  const directionsRenderer = new google.maps.DirectionsRenderer();
  const infoWindow = new google.maps.InfoWindow();
  const map = new google.maps.Map(document.getElementById("map"), {
    zoom: centerZoom,
    center: { lat: centerLatitude, lng: centerLongitude },
  });

//  directionsRenderer.setMap(map);

//  const onChangeHandler = function () {
//    calculateAndDisplayRoute(directionsService, directionsRenderer);
//  };
//
//  document.getElementById("start").addEventListener("change", onChangeHandler);
//  document.getElementById("end").addEventListener("change", onChangeHandler);

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

//
window.addEventListener("load", function() {
         let input = document.getElementById("originInput");
         google.maps.event.addDomListener(input, "keydown", function(event) {
         if (event.keyCode === 13){
           event.preventDefault();
           //document.getElementById("submitBtn").click();
           }
           console.log("This form is working")
         });
      });

//      let input = document.getElementById("originInput");
//
//      input.addEventListener("keydown", function(event) {
//           if (event.keyCode === 13){
//             event.preventDefault();
//             document.getElementById("submitBtn").click();
//             }
//      });

//var inputText = document.getElementById("inputField");
//   inputText.addEventListener("keyup", function(event) {
//      if (event.keyCode === 13) {
//         event.preventDefault();
//         document.getElementById("alertBtn").click();
//      }
//   });
      //add event listener to button that allows for form to be submitted when clicked


