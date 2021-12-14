package com.roadtriprunner.RoadTripRunner.controllers;

import com.roadtriprunner.RoadTripRunner.data.TripRepository;
import com.roadtriprunner.RoadTripRunner.models.DetourRadius;
import com.roadtriprunner.RoadTripRunner.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    TripRepository tripRepository;

    @GetMapping("/")
    public String displayTripForm(Model model){
        model.addAttribute("title", "Enter Your Starting and Ending Locations");
        model.addAttribute("detourRadiuses", DetourRadius.values());
        model.addAttribute("trip", new Trip() );



        return "index";
    }

    @PostMapping("/")
    public String processRouteForm(@ModelAttribute @Valid Trip newTrip, Errors errors, Model model){
        //save a new trip
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "index";
        }

        tripRepository.save(newTrip);

        System.out.println("Starting location: " + newTrip.getStartingLocation());
        System.out.println("Ending location: " + newTrip.getEndingLocation());

        return "redirect:";
    }

}
