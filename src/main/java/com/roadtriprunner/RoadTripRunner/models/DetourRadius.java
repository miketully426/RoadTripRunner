package com.roadtriprunner.RoadTripRunner.models;

public enum DetourRadius {
    FIVE_MILES (5),
    TWENTY_FIVE_MILES (25),
    FIFTY_MILES (50),
    ONE_HUNDRED_MILES (100),
    ONE_HUNDRED_FIFTY_PLUS (150);

    private final int intRadius;


    DetourRadius(int intRadius) {
        this.intRadius = intRadius;
    }

    public int getIntRadius() {
        return intRadius;
    }

}
