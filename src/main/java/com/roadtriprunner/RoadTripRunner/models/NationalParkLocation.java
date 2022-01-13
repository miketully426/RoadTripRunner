package com.roadtriprunner.RoadTripRunner.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

@Data
public class NationalParkLocation {

    private String id;
    private String url;
    private String fullName;
    private String parkCode;
    private String description;
    private String latitude;
    private String longitude;
    private String latLong;
    private String activities;
    private String topics;
    private String states;
    private String contacts;
    private String entranceFees;
    private String entrancePasses;
    private String fees;
    private String directionsInfo;
    private String directionsUrl;
    private String operatingHours;
    private String addresses;
    private String images;
    private String weatherInfo;
    private String name;
    private String designation;


    public NationalParkLocation() {};




}
