package com.roadtriprunner.RoadTripRunner.models.dto;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RouteDTO {

    @NotBlank(message = "Starting location is required.")
    @NotNull
    private String startingLocation;

    @NotBlank(message="Ending location is required.")
    @NotNull
    private String endingLocation;

    @NotBlank(message="Please name your trip to save it!")
    @NotNull
    private String tripName;

}
