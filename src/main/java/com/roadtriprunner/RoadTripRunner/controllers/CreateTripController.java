package com.roadtriprunner.RoadTripRunner.controllers;

import com.roadtriprunner.RoadTripRunner.data.TripRepository;
import com.roadtriprunner.RoadTripRunner.data.UserRepository;
import com.roadtriprunner.RoadTripRunner.models.Trip;
import com.roadtriprunner.RoadTripRunner.models.User;
import com.roadtriprunner.RoadTripRunner.models.dto.RouteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
    UserRepository userRepository;

    @Autowired
    TripRepository tripRepository;


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
    public String renderPlanATripPage(Model model, RouteDTO routeDTO, HttpServletRequest request) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        model.addAttribute("trip", new Trip());
        return "planATrip";
    }

    @PostMapping("/planATrip")
    public String processRouteForm(@ModelAttribute @Valid Trip newTrip, Errors errors, Model model, RouteDTO routeDTO, HttpServletRequest request) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "landing";
        }
        Trip trip = new Trip(routeDTO.getStartingLocation(), routeDTO.getEndingLocation());
        tripRepository.save(trip);
        return "redirect:";
    }

}
