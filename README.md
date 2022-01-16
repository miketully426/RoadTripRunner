# RoadTrip Runner

## Description
RoadTrip Runner provides users with the tools to create carefully-curated and memorable road trips. Authorized users interact with a clear and easy-to-use interface in which users enter their starting and ending locations, and then the application renders a map with those locations as well as all National Parks along their route. Users can select National Parks to add to their trip and then save the trip for future reference. RoadTrip Runner allows the everyday user, young or old, to plan their ideal National Park tour.

## Features
1. **Authentication and Authorization**: 
Individuals must register with the app, using a complex password, and their login information is saved to the database. Users login to utilize the app. <br>


2. **Autocomplete**: When planning a trip, users will be aided by the Google Maps Autocomplete API when entering in starting locations, destinations, and choosing National Parks to add to their trip.    


3. **Informative and Interactive Map**: When planning a trip, users see an interactive map that displays the starting and ending locations, the most efficient driving pathway connecting the two locations, and all National Parks within 100 miles of their route. Each National Park is pinned with a marker and a comprehensive information window to assist the user in determining which National Park would be best suited for them. Both the Google Maps API and the National Park Service API feed into the map, so the information is guaranteed to be up-to-date at all times. 


4. **Trip Repository**: Users may save their trips, which links the user to the trip within the database, and then the user may view the trip at a future point.


5. **Demo Video**: Guests and users can view a demonstration video that details the features of the app.


## Future Development
* Restructure the coding approach to use more of the Java Client for Google Maps Services.
* Have the user add National Parks to their trip in an easier way. Ideas include clicking on the marker or on checkboxes of options.
* Use the Google Places API or other APIs to include other possible locations to add to the user's route. Filter locations by price point, category, etc.
* Allow the user to suggest possible locations to be included in the location database for other users to select. Incorporate administration access to approve these user suggested places.
* Have the user have the ability to choose the detour radius.
* Have an itinerary page that easily allows the user to print the trip.
* Style the project with Angular rather than Thymeleaf.

## Authors and Acknowledgment
|Authors   |Acknowledgment   |
|---|---|
| [Emily Gerst](https://github.com/emmykg38) |  [Google Maps Platform](https://developers.google.com/maps) |
| [Melanie Gunn](https://github.com/mgunnhawkins) |  [National Park Service](https://www.nps.gov/subjects/developer/api-documentation.htm) |
| [Valerie Tonsor](https://github.com/valerietonsor/valerietonsor) |  [Jonas Hermsmeier](https://github.com/jhermsmeier/node-google-polyline/blob/master/lib/decode.js) |
| [Krystal White](https://github.com/krystalwhite)  | [Mike Tully](https://github.com/miketully426/)  |


## License
[GNU General Public License](https://choosealicense.com/licenses/gpl-3.0/)