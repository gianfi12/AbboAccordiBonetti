package com.SafeStreets.model;

import java.time.Instant;

public class Report {
    private Instant reportInstant;
    private Instant instantOfWatchedViolation;
    private Place place;
    private ViolationType violationType;
    private String description;
    private Vehicle vehicle;

    public Report(Instant reportInstant, Instant instantOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle) {
        this.reportInstant = reportInstant;
        this.instantOfWatchedViolation = instantOfWatchedViolation;
        this.place = place;
        this.violationType = violationType;
        this.description = description;
        this.vehicle = vehicle;
    }

    public Instant getReportInstant() {
        return reportInstant;
    }

    public Instant getInstantOfWatchedViolation() {
        return instantOfWatchedViolation;
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
}
