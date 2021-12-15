package com.roadtriprunner.RoadTripRunner.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Trip  extends AbstractEntity {

    private String tripName;

    @NotBlank(message = "Starting location is required.")
    @NotNull
    private String startingLocation;

    @NotBlank(message="Ending location is required.")
    @NotNull
    private String endingLocation;

    @Min(0)
    @Max(150)
    private Integer detourRadius;

    public Trip() { }

    public Trip(String tripName, String startingLocation, String endingLocation, Integer detourRadius) {
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

    public Integer getDetourRadius() {
        return detourRadius;
    }

    public void setDetourRadius(Integer detourRadius) {
        this.detourRadius = detourRadius;
    }
}
