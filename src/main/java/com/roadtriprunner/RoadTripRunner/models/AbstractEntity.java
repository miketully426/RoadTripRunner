
package com.roadtriprunner.RoadTripRunner.models;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    private int id;

    public int getId() { return id; }
}