package com.roadtriprunner.RoadTripRunner.models;

import lombok.Data;

public class Location {

    private double latitude;

    private double longitude;

    private double[] location;

    private String name;

    private String description;



    public Location(double latitude, double longitude, double[] location, String name, String description) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.name = name;
        this.description = description;
    }
}
