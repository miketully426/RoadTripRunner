package com.roadtriprunner.RoadTripRunner.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class CreateTripController {


    @Value("${gmapsApiKey}")
    private String gmapsApiKey;



    @GetMapping("/planATrip")
    public String renderPlanATripPage(Model model) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        return "planATrip";
    }

}
