package com.roadtriprunner.RoadTripRunner.controllers;


import com.roadtriprunner.RoadTripRunner.data.UserRepository;
import com.roadtriprunner.RoadTripRunner.models.User;
import org.json.JSONArray;
import org.json.JSONObject;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class HomeController {

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


    @Value("${gmapsApiKey}")
    private String gmapsApiKey;


    @GetMapping("/")
    public String displayTripForm(Model model, HttpServletRequest request) {
        model.addAttribute("title", "Enter Your Starting and Ending Locations");
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        model.addAttribute("trip", new Trip());
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        return "index";
    }

    @GetMapping("/map")
    public String index(Model model, HttpServletRequest request) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        return "maps/mapDisplay";
    }

    @PostMapping("/")
    public String processRouteForm(@ModelAttribute @Valid Trip newTrip, Errors errors, Model model, HttpServletRequest request){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "index";
        }
        tripRepository.save(newTrip);
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        return "redirect:";
    }
}
