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
        model.addAttribute(new RouteDTO());
        return "planATrip";
    }

    @PostMapping("/planATrip")
    public String processRouteForm(@ModelAttribute @Valid RouteDTO routeDTO, Errors errors, Model model, Trip newTrip, HttpServletRequest request) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        User theUser = getUserFromSession(request.getSession());
        model.addAttribute("loggedInUser", theUser);
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "/planATrip";
        }
        Trip trip = new Trip(routeDTO.getTripName(), routeDTO.getStartingLocation(), routeDTO.getEndingLocation(), newTrip.getStopOne(), newTrip.getStopTwo(), newTrip.getStopThree(), theUser);
        tripRepository.save(trip);
        return "redirect:";
    }

}
