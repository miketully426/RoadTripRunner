package com.roadtriprunner.RoadTripRunner.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class Location {


    private String lat;

    private String lng;


}
