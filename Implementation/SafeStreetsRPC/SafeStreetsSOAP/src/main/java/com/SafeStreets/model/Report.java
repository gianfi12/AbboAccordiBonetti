package com.SafeStreets.model;

import java.time.LocalTime;
import java.util.Date;

public class Report {
    private Date reportDate;
    private LocalTime reportTime;
    private Date dateOfWatchedViolation;
    private LocalTime timeOfWatchedViolation;
    private Place place;
    private ViolationType violationType;
    private String description;
    private Vehicle vehicle;

    public Report(Date reportDate, LocalTime reportTime, Date dateOfWatchedViolation, LocalTime timeOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle) {
        this.reportDate = reportDate;
        this.reportTime = reportTime;
        this.dateOfWatchedViolation = dateOfWatchedViolation;
        this.timeOfWatchedViolation = timeOfWatchedViolation;
        this.place = place;
        this.violationType = violationType;
        this.description = description;
        this.vehicle = vehicle;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public LocalTime getReportTime() {
        return reportTime;
    }

    public Date getDateOfWatchedViolation() {
        return dateOfWatchedViolation;
    }

    public LocalTime getTimeOfWatchedViolation() {
        return timeOfWatchedViolation;
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
