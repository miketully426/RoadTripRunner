package com.roadtriprunner.RoadTripRunner.controllers;


import com.roadtriprunner.RoadTripRunner.data.TripRepository;
import com.roadtriprunner.RoadTripRunner.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/trips")
public class TripController {

    @Autowired
    TripRepository tripRepository;

    @GetMapping("")
    public String displayAllTrips(Model model) {
            model.addAttribute("title", "Your saved trips");
            model.addAttribute("trips", tripRepository.findAll());
        return "trips/index";
    }

    @GetMapping("/trip")
    public String displayASingleTrip(@RequestParam Integer tripId, Model model) {

        Optional<Trip> possibleTrip = tripRepository.findById(tripId);

        if (possibleTrip.isEmpty()) {
            model.addAttribute("title", "Oops! That trip doesn't exist yet!");
        }
        else {
            Trip trip = possibleTrip.get();
            model.addAttribute("title", trip.getTripName());
            model.addAttribute("trip", trip);
        }
        return "trips/trip";
    }

}
