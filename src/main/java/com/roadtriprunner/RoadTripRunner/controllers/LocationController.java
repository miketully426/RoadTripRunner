package com.roadtriprunner.RoadTripRunner.controllers;

import com.roadtriprunner.RoadTripRunner.data.TripRepository;
import com.roadtriprunner.RoadTripRunner.models.Trip;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@RestController
@RequestMapping("/map")
public class LocationController {

    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public String viewNationalParks(HttpServletRequest request, Model model) {
        return "/mapDisplay";
    }


}


//    eventually will be added to connect to a trip
//    @Autowired
//    TripRepository tripRepository;

//    @GetMapping
//    public List<Trip> getAllTrips() {
//        return tripRepository.findAll();
//    }






//    @GetMapping()
//    public JSONObject displayNationalParks() {
//        return new JSONObject("{'geometry.location'}");
//    }