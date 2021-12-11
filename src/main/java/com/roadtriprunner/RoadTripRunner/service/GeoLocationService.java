package com.roadtriprunner.RoadTripRunner.service;
import com.roadtriprunner.RoadTripRunner.domain.GeoLocation;

import java.util.Optional;

public interface GeoLocationService {
    Optional<GeoLocation> computeGeoLocation(String fullAddressLine);
}

