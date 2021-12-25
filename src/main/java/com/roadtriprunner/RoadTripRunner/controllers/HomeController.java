package com.roadtriprunner.RoadTripRunner.controllers;

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
import javax.validation.Valid;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    TripRepository tripRepository;

    @Value("${gmapsApiKey}")
    private String gmapsApiKey;

    @GetMapping("/")
    public String displayTripForm(Model model) {
        model.addAttribute("title", "Enter Your Starting and Ending Locations");
        model.addAttribute("trip", new Trip());
        return "index";
    }

    @GetMapping("/map")
    public String index(Model model) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);
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

    @RequestMapping("/map")
    public String displayNationalParkMarker(Model model) {
        model.addAttribute("gmapsApiKey", gmapsApiKey);


        ArrayList<JSONObject> listdata = new ArrayList<JSONObject>();
//        JSONArray jArray = (JSONArray)jsonObject;


        model.addAttribute("listOfNationalParks", "listOfNationalParks");
        return "/maps/mapDisplay";
    }



}


//    ArrayList<String> listdata = new ArrayList<String>();
//    JSONArray jArray = (JSONArray)jsonObject;
//        if (jArray != null) {
//                for (int i=0;i<jArray.length();i++){
//        listdata.add(jArray.getString(i));
//        }
//        }