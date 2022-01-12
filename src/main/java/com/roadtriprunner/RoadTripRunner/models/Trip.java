package com.roadtriprunner.RoadTripRunner.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Trip  extends AbstractEntity {


//    @NotBlank(message = "Please name your trip to save it!")
//    @NotNull
//    private String tripName;

    @NotBlank(message = "Starting location is required.")
    @NotNull
    private String startingLocation;

    @NotBlank(message="Ending location is required.")
    @NotNull
    private String endingLocation;

    public Trip(String startingLocation, String endingLocation) {
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
    }
}
