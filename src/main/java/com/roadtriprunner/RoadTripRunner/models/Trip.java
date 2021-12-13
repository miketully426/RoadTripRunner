package com.roadtriprunner.RoadTripRunner.models;

import javax.persistence.Entity;

@Entity
public class Trip  extends AbstractEntity{
    private String tripName;

    private Location startingLocation;

    private Location endingLocation;

    private double detourDistance;

    public Trip(String tripName, Location startingLocation, Location endingLocation, double detourDistance) {
        this.tripName = tripName;
        this.startingLocation = startingLocation;
        this.endingLocation = endingLocation;
        this.detourDistance = detourDistance;
    }

    public String getTripName() { return tripName; }

    public void setTripName(String tripName) { this.tripName = tripName; }

    public Location getStartingLocation() { return startingLocation; }

    public void setStartingLocation(Location startingLocation) { this.startingLocation = startingLocation; }

    public Location getEndingLocation() { return endingLocation; }

    public void setEndingLocation(Location endingLocation) { this.endingLocation = endingLocation; }

    public double getDetourDistance() { return detourDistance; }

    public void setDetourDistance(double detourDistance) { this.detourDistance = detourDistance; }
}
