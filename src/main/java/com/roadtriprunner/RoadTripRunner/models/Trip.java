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
    @NotNull
    private String startingLocation;

    @NotBlank(message="Ending location is required.")
    @NotNull
    private String endingLocation;

    public Trip (String startingLocation, String endingLocation) {
        this.endingLocation = endingLocation;
        this.startingLocation = startingLocation;
    }

    public Trip() {};

    ArrayList<String> itinerary = new ArrayList<>();
//itinerary[0] = startingLocation, [1] = endingLocation, .push() other names of locations
}
