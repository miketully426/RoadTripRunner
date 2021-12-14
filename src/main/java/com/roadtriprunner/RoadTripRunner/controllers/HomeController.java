package com.roadtriprunner.RoadTripRunner.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Value("${gmapsApiKey}")
    private String gmapsApiKey;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
        return "index";
    }
}
