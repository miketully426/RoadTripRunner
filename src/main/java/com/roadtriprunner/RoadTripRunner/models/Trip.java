package com.roadtriprunner.RoadTripRunner.models;

import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Trip  extends AbstractEntity {

//    GeoApiContext context = new GeoApiContext.Builder().apiKey("KEY HERE").build();


//    DirectionsRoute route = new DirectionsApi.newRequest(context)


//    @NotBlank(message = "Please name your trip to save it!")
//    @NotNull
//    private String tripName;

    @NotBlank(message = "Starting location is required.")
    @NotNull
    private String startingLocation;

    @NotBlank(message="Ending location is required.")
    @NotNull
    private String endingLocation;

    public Trip(String startingLocation, String endingLocation) throws IOException, InterruptedException, ApiException {
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
    }



}
