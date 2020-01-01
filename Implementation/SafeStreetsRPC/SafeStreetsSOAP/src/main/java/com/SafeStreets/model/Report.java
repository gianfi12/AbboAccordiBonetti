package com.SafeStreets.model;

import java.time.Instant;
import java.time.OffsetDateTime;

public class Report {
    private OffsetDateTime reportOffsetDateTime;
    private OffsetDateTime odtOfWatchedViolation;
    private Place place;
    private ViolationType violationType;
    private String description;
    private Vehicle vehicle;

    public Report(OffsetDateTime reportOffsetDateTime, OffsetDateTime odtOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle) {
        this.reportOffsetDateTime = reportOffsetDateTime;
        this.odtOfWatchedViolation = odtOfWatchedViolation;
        this.place = place;
        this.violationType = violationType;
        this.description = description;
        this.vehicle = vehicle;
    }

    public OffsetDateTime getReportOffsetDateTime() {
        return reportOffsetDateTime;
    }

    public OffsetDateTime getOdtOfWatchedViolation() {
        return odtOfWatchedViolation;
    }

    public Place getPlace() {
        return place;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public String getDescription() {
        return description;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
    public static Report fromJSON(String jsonString){
        //TODO
        return null;
    }
}
