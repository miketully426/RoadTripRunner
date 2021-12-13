package com.roadtriprunner.RoadTripRunner.models;

public enum DetourDistance {
    FIVE_MILES (5, "5 miles"),
    TWENTY_FIVE_MILES (25, "25 miles"),
    FIFTY_MILES (50, "50 miles"),
    ONE_HUNDRED_MILES (100, "100 miles"),
    ONE_HUNDRED_FIFTY_PLUS (150, "150+ miles");

    private final int distance;
    private String stringDetourDistance;


    DetourDistance(int distance, String stringDetourDistance) {
        this.distance = distance;
        this.stringDetourDistance= stringDetourDistance;
    }


}
