package com.roadtriprunner.RoadTripRunner;

import com.google.maps.GeoApiContext;
import com.google.maps.StaticMapsRequest;

public class GoogleMapsApi {

    static GeoApiContext context = new GeoApiContext.Builder().apiKey("API KEY").build();

    public static void setMap() {
        StaticMapsRequest map = new StaticMapsRequest(context);
        map.center("Kansas City, MO");
        map.zoom(4);
    }

}
