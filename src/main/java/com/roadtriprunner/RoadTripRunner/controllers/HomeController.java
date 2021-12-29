package com.roadtriprunner.RoadTripRunner.controllers;

import com.roadtriprunner.RoadTripRunner.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.roadtriprunner.RoadTripRunner.data.TripRepository;
import com.roadtriprunner.RoadTripRunner.models.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    TripRepository tripRepository;

    @Value("${gmapsApiKey}")
    private String gmapsApiKey;


    @GetMapping("/")
    public String displayTripForm(Model model, User theUser) {
        model.addAttribute("title", "Enter Your Starting and Ending Locations");
        model.addAttribute("trip", new Trip());
        model.addAttribute("isUserInSession", theUser.getUserInSession());
        return "index";
    }

    @GetMapping("/map")
    public String index(Model model, User theUser) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        model.addAttribute("isUserInSession", theUser.getUserInSession());
        return "maps/mapDisplay";
    }

    @PostMapping("/")
    public String processRouteForm(@ModelAttribute @Valid Trip newTrip, Errors errors, Model model){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "index";
        }
        tripRepository.save(newTrip);
        return "redirect:";
    }

}
