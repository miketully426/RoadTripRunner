package com.roadtriprunner.RoadTripRunner.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.roadtriprunner.RoadTripRunner.data.UserRepository;
import com.roadtriprunner.RoadTripRunner.models.User;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

    @Autowired
    UserRepository userRepository;

    String address = "Owensboro, KY";

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

//String initMapCall = "https://maps.googleapis.com/maps/api/js?key=" + gmapsApiKey + "&libraries=places&callback=initMap";

    @SneakyThrows
    @GetMapping("/planATrip")
    public String renderPlanATripPage(Model model, HttpServletRequest request) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        callAPIForLatLng();
        return "planATrip";
    }

    public void callAPIForLatLng() throws IOException, InterruptedException, ApiException {
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(gmapsApiKey)
                .build();
        GeocodingResult[] results = GeocodingApi.geocode(context,
                address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location));
        context.shutdown();
    }
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest apiRequest = HttpRequest.newBuilder().uri(URI.create(initMapCall)).build();
//        Void httpResponse = client.sendAsync(apiRequest, HttpResponse.BodyHandlers.ofString())
//                .thenApply(HttpResponse::body)
//                .thenAccept(System.out::println)
//                .join();
//
//        model.addAttribute("httpResponse", httpResponse);
//        call setCallback(Callback<t> callback) method




}

