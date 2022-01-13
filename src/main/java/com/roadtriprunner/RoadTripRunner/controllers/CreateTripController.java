package com.roadtriprunner.RoadTripRunner.controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
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
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

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

    GeoApiContext context = new GeoApiContext.Builder().apiKey("API KEY").build();


    @SneakyThrows
    public void getNationalParks() {
        HttpClient client = HttpClient.newBuilder().build();
        String nationalParkUrl = "https://developer.nps.gov/api/v1/parks?limit=600&api_key=" + natParkApiKey;

        HttpRequest nationalParkRequest = HttpRequest.newBuilder()
                .uri(URI.create(nationalParkUrl))
                .timeout(Duration.ofMinutes(2))
                .build();
        client.sendAsync(nationalParkRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(CreateTripController::convertJsonToCSV())
//                .thenAccept(System.out::println);
    }

    @SneakyThrows
    public static void convertJsonToCSV() throws IOException {
        JsonNode jsonTree = new ObjectMapper().readTree(new File("src/main/resources/nationalParks.json"));
        CsvSchema.Builder csvSchemaBuilder = CsvSchema.builder();
        JsonNode firstObject = jsonTree.elements().next();
        firstObject.fieldNames().forEachRemaining(fieldName -> {csvSchemaBuilder.addColumn(fieldName);} );
        CsvSchema csvSchema = csvSchemaBuilder.build().withHeader();

        CsvMapper csvMapper = new CsvMapper();
        csvMapper.writerFor(JsonNode.class)
                .with(csvSchema)
                .writeValue(new File("src/main/resources/nationalParks.csv"), jsonTree);
    }


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


    @GetMapping("/planATrip")
    public String renderPlanATripPage(Model model, HttpServletRequest request, DirectionsDTO directionsDTO) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        getNationalParks();
        return "planATrip";
    }



    @PostMapping("/planATrip")
    public String processRouteForm(@ModelAttribute @Valid Trip newTrip, Errors errors, Model model, DirectionsDTO directionsDTO) throws IOException, InterruptedException, ApiException {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "index";
        }

        Trip trip = new Trip(directionsDTO.getStartingLocation(), directionsDTO.getEndingLocation());
        System.out.println(trip.toString());
        tripRepository.save(trip);

        callAPIForLatLng(trip.getStartingLocation());
        callAPIForLatLng(trip.getEndingLocation());
        //must call context.shutdown last to end connection to API
        context.shutdown();
        return "redirect:";
    }


    public void callAPIForLatLng(String address) throws IOException, InterruptedException, ApiException {

        GeocodingResult[] results = GeocodingApi.geocode(context,
                address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location));
    }

}
