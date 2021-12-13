package com.roadtriprunner.RoadTripRunner.data;

import com.roadtriprunner.RoadTripRunner.models.Trip;
import com.roadtriprunner.RoadTripRunner.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface TripRepository extends CrudRepository<Trip, Integer> {

}