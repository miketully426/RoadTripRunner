package com.roadtriprunner.RoadTripRunner.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Trip  extends AbstractEntity {


    private String tripName;

    @NotBlank(message = "Starting location is required.")
    private String startingLocation;

    @NotBlank(message="Ending location is required.")
    private String endingLocation;

    public Trip (String startingLocation, String endingLocation) {
        this.endingLocation = endingLocation;
        this.startingLocation = startingLocation;
    }

    public Trip(String tripName, String startingLocation, String endingLocation, String stopOne, String stopTwo, String stopThree) {
        this.tripName = tripName;
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.stopOne = stopOne;
        this.stopTwo = stopTwo;
        this.stopThree = stopThree;
    }

    public Trip() {};

    private String stopOne;

    private String stopTwo;

    private String stopThree;

}
