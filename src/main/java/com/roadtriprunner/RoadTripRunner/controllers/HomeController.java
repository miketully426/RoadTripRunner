package com.roadtriprunner.RoadTripRunner.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {
    @GetMapping
    public String displayRouteForm(Model model){
        model.addAttribute("title", "Enter Your Starting and Ending Locations");
        return "redirect:";
    }

    @PostMapping
    public String processRouteForm(@ModelAttribute Model model, Errors errors){
        if (errors.hasErrors()) {
            model.addAttribute("title", "Enter Your Starting and Ending Locations");
            return "redirect:";
        }

        System.out.println("locations entered");
        return "redirect";
    }

}
