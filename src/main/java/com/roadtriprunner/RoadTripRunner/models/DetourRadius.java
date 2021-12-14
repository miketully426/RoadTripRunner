package com.roadtriprunner.RoadTripRunner.models;

public enum DetourRadius {
    FIVE_MILES (5, "5 miles"),
    TWENTY_FIVE_MILES (25, "25 miles"),
    FIFTY_MILES (50, "50 miles"),
    ONE_HUNDRED_MILES (100, "100 miles"),
    ONE_HUNDRED_FIFTY_PLUS (150, "150+ miles");

    private final int radius;
    private String stringDetourRadius;


    DetourRadius(int radius, String stringDetourRadius) {
        this.radius = radius;
        this.stringDetourRadius= stringDetourRadius;
    }

    public int getRadius() {
        return radius;
    }

    public String getStringDetourRadius() {
        return stringDetourRadius;
    }
}
