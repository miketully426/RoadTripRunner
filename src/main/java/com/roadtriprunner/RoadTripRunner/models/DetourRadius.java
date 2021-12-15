package com.roadtriprunner.RoadTripRunner.models;

public enum DetourRadius {
    FIVE_MILES (5, "5 miles"),
    TWENTY_FIVE_MILES (25, "25 miles"),
    FIFTY_MILES (50, "50 miles"),
    ONE_HUNDRED_MILES (100, "100 miles"),
    ONE_HUNDRED_FIFTY_PLUS (150, "150+ miles");

    private final int intDetourRadius;
    private final String stringDetourRadius;


    DetourRadius(int intDetourRadius, String stringDetourRadius) {
        this.intDetourRadius = intDetourRadius;
        this.stringDetourRadius= stringDetourRadius;
    }

    public int getIntDetourRadius() {
        return intDetourRadius;
    }

    public String getStringDetourRadius() {
        return stringDetourRadius;
    }
}
