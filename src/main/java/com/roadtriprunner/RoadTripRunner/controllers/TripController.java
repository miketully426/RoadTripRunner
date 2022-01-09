package com.roadtriprunner.RoadTripRunner.controllers;


import com.roadtriprunner.RoadTripRunner.data.TripRepository;
import com.roadtriprunner.RoadTripRunner.data.UserRepository;
import com.roadtriprunner.RoadTripRunner.models.Trip;
import com.roadtriprunner.RoadTripRunner.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/trips")
public class TripController {

    @Autowired
    TripRepository tripRepository;

    @Autowired
    UserRepository userRepository;

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


    @GetMapping("")
    public String displayAllTrips(Model model, HttpServletRequest request) {
            model.addAttribute("title", "Your saved trips");
            model.addAttribute("trips", tripRepository.findAll());
            User theUser = getUserFromSession(request.getSession());
            model.addAttribute("loggedInUser", theUser);
        return "trips/index";
    }

    @GetMapping("/trip")
    public String displayASingleTrip(@RequestParam Integer tripId, Model model, HttpServletRequest request) {

        Optional<Trip> possibleTrip = tripRepository.findById(tripId);

        if (possibleTrip.isEmpty()) {
            model.addAttribute("title", "Oops! That trip doesn't exist yet!");
        }
        else {
            Trip trip = possibleTrip.get();
            model.addAttribute("title", trip.getTripName());
            model.addAttribute("trip", trip);
            User theUser = getUserFromSession(request.getSession());
            model.addAttribute("loggedInUser", theUser);
        }
        return "trips/trip";
    }

}
