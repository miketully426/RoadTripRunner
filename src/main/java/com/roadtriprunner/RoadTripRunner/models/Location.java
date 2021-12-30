package com.roadtriprunner.RoadTripRunner.models;


//class to store autocomplete object on inputs 'originInput' and 'destinationInput'
public class Location {

    //jsonAutoObject field of 'name'
    private String name;

    //corresponds to jsonAutoObject(origin/destination) of 'formatted_address'
    private String address;

    //jsonAutoObject field 'geometry.location.lat'
    //may need to be declared as a different type?
    private double latitude;

    //jsonAutoObject field 'geometry.location.lng'
    private double longitude;






}
