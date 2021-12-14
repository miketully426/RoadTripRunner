package com.roadtriprunner.RoadTripRunner.models;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Trip  extends AbstractEntity {

    private String tripName;

    @NotBlank
    @NotNull
    private String startingLocation;

    @NotBlank
    @NotNull
    private String endingLocation;

    private DetourRadius detourRadius;

    public Trip() { }

    public Trip(String tripName, String startingLocation, String endingLocation, DetourRadius detourRadius) {
        this();
        this.tripName = tripName;
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.detourRadius = detourRadius;
    }

    public String getTripName() { return tripName; }

    public void setTripName(String tripName) { this.tripName = tripName; }

    public String getStartingLocation() { return startingLocation;
    }

    public void setStartingLocation(String startingLocation) {
        this.startingLocation = startingLocation;
    }

    public String getEndingLocation() {
        return endingLocation;
    }

    public void setEndingLocation(String endingLocation) {
        this.endingLocation = endingLocation;
    }

    public DetourRadius getDetourRadius() {
        return detourRadius;
    }
}
