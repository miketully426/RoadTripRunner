package com.roadtriprunner.RoadTripRunner.models;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class Trip  extends AbstractEntity {


    private String tripName;

    @NotBlank(message = "Starting location is required.")
    private String startingLocation;

    @NotBlank(message="Ending location is required.")
    private String endingLocation;

    private String stopOne;

    private String stopTwo;

    private String stopThree;

    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
