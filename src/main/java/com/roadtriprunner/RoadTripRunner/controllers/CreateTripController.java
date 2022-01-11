package com.roadtriprunner.RoadTripRunner.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Optional;

@Controller
@RequestMapping("/")
public class CreateTripController {

    @Value("${gmapsApiKey}")
    private String gmapsApiKey;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserRepository userRepository;

    ObjectMapper mapper = new ObjectMapper();

    GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyDH10rziyorFJcwhqYFPCxO61ExsmdLp20").build();

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
    public String renderPlanATripPage(Model model, HttpServletRequest request) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        model.addAttribute("trip", new Trip());
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        return "planATrip";
    }


    @PostMapping("/planATrip")
    public String processRouteForm(@ModelAttribute @Valid Trip newTrip, Errors errors, Model model) throws IOException, InterruptedException, ApiException {
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "index";
        }

        tripRepository.save(newTrip);
        return "redirect:";
    }


    public void callAPIForLatLng(String address) throws IOException, InterruptedException, ApiException {
        GeocodingResult[] results = GeocodingApi.geocode(context,
                address).await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(results[0].geometry.location));
        context.shutdown();
    }

}
