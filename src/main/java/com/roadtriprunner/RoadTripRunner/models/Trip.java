package com.roadtriprunner.RoadTripRunner.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.xml.stream.Location;

@Entity
public class Trip  extends AbstractEntity {
    private String tripName;

    @NotBlank
    private String startingLocation;

    @NotBlank
    private String endingLocation;

//    private Enum detourDistance;

    public Trip() { }

    public Trip(String tripName, String startingLocation, String endingLocation) {
        this.tripName = tripName;
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
//        this.detourDistance = detourDistance;
    }

    public String getTripName() { return tripName; }

    public void setTripName(String tripName) { this.tripName = tripName; }

}
