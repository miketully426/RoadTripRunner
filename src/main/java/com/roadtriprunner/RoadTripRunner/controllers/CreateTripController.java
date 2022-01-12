package com.roadtriprunner.RoadTripRunner.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.roadtriprunner.RoadTripRunner.data.TripRepository;
import com.roadtriprunner.RoadTripRunner.data.UserRepository;
import com.roadtriprunner.RoadTripRunner.models.Trip;
import com.roadtriprunner.RoadTripRunner.models.User;
import com.roadtriprunner.RoadTripRunner.models.dto.DirectionsDTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class CreateTripController {

    @Value("${gmapsApiKey}")
    private String gmapsApiKey;

    @Value("${natParkApiKey}")
    private String natParkApiKey;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserRepository userRepository;

    ObjectMapper mapper = new ObjectMapper();

//    "https://developer.nps.gov/api/v1/parks?parkCode=acad&api_key=yRmHcolzaKy9VCZB55w4d4s2Km33ssxixl36mPuo"


    //FOR NATIONAL PARKS API:
//
//    String nationalParkUrl = "https://developer.nps.gov/api/v1/parks?limit=600";
//    Httpheader = {"Authorization":"yRmHcolzaKy9VCZB55w4d4s2Km33ssxixl36mPuo"};




//            endpoint = "https://developer.nps.gov/api/v1/parks?limit=600"
//            HEADERS = {"Authorization":"INSERT-API-KEY-HERE"}
//            req = urllib.request.Request(endpoint,headers=HEADERS)
//
//            # Execute request and parse response
//            response = urllib.request.urlopen(req).read()
//            data = json.loads(response.decode('utf-8'))
//
//            # Prepare and execute output
//            for park in data["data"]:
//            print(park["fullName"])

//    public JSONObject sendPostForNationalParks() {
//        HttpClient httpClient = HttpClient.newBuilder().build();
//        HttpRequest request = new HttpRequest() {}
//
//        request.addHeader("content-type", "application/json");
//        request.addHeader("Authorization", "Bearer " + this.Access_Token);
//        request.setEntity(params);
//
//            HttpResponse response = httpClient.execute(request);
//
//            System.out.println("API Resonse :"+result.toString());
//            jsonObject = new JSONObject(result.toString());
//
//    }



    GeoApiContext context = new GeoApiContext.Builder().apiKey("API KEY").build();




//    String address = "Owensboro, KY"; //sample address


    public User getUserFromSession(HttpSession session) {
        Integer userId = (Integer) session.getAttribute(AuthenticationController.userSessionKey);
        if (userId == null) {
            return null;
        }

        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            return null;
        }

        return user.get();
    }

    @SneakyThrows
    @GetMapping("/planATrip")
    public String renderPlanATripPage(Model model, HttpServletRequest request, DirectionsDTO directionsDTO) throws IOException, InterruptedException, ApiException {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
//        model.addAttribute("trip", new Trip());
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        callNationalParkAPI();
        return "planATrip";
    }



//    //annotations here...? responsebody, requestmapping -- value at origin?
//    public void renderTripRequest(DirectionsDTO directionsDTO) throws IOException, InterruptedException, ApiException {
//        DirectionsApiRequest request = new DirectionsApiRequest(context);
//        DirectionsResult route = request.origin(directionsDTO.getStartingLocation()).destination(directionsDTO.getEndingLocation()).await();
//    }


    @PostMapping("/planATrip")
    public String processRouteForm(@ModelAttribute @Valid Trip newTrip, Errors errors, Model model, DirectionsDTO directionsDTO) throws IOException, InterruptedException, ApiException {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "index";
        }
//        callAPIForLatLng(directionsDTO.getStartingLocation());
//        callAPIForLatLng(directionsDTO.getEndingLocation());
////        System.out.println("The starting location is " + startingLocation + " and the ending location is " + endingLocation);
//        Trip trip = new Trip(directionsDTO.getStartingLocation(), directionsDTO.getEndingLocation());
//        System.out.println(trip.toString());
//        tripRepository.save(trip);
//        Trip trip = new Trip(directionsDTO.getStartingLocation(), directionsDTO.getEndingLocation());
//        System.out.println(trip.toString());
//        tripRepository.save(trip);
        return "redirect:";
    }

/*
    public void callAPIForLatLng(String address) throws IOException, InterruptedException, ApiException {
        GeocodingResult[] results = GeocodingApi.geocode(context,
                address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location));
        context.shutdown();
    }*/

    public void callNationalParkAPI() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest apiRequest = HttpRequest.newBuilder().uri(URI.create("https://developer.nps.gov/api/v1/parks?limit=500&api_key=" + natParkApiKey)).build();
    Void httpResponse = client.sendAsync(apiRequest, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenAccept(System.out::println)
            .join();
//            .thenApply(CreateTripController::parse)
    }

    /* parse method doesn't work because the response body is not converted to JSONArray?
    public static String parse(String responseBody) {
        JSONArray locations = new JSONArray(responseBody);
        for (int i = 0; i < locations.length(); i++){
            JSONObject location = locations.getJSONObject(i);
            String name = location.getString("name");
            System.out.println(name);
        }
        return null;
    }*/
}
