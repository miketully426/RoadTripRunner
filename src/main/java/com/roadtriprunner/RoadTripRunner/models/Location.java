package com.roadtriprunner.RoadTripRunner.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

//class to store autocomplete object on inputs 'originInput' and 'destinationInput'
@Getter
@Setter
public class Location {

    //jsonAutoObject field of 'name'
    private String name;

    //corresponds to jsonAutoObject(origin/destination) of 'formatted_address'
    @JsonProperty("formatted_address")
    private String address;

    //jsonAutoObject field 'geometry.location.lat'
    //may need to be declared as a different type?
    private double lat;

    //jsonAutoObject field 'geometry.location.lng'
    private double lng;

    public Location(String name, String address, double lat, double lng) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
    }

    public Location() {}


}