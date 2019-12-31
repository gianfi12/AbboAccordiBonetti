package com.SafeStreets.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Statistic {
    private Place street;
    private List<Coordinate> coordinateListForStreet;
    private int numberOfViolationsInStreet;
    private int numberOfReports;
    private int numberOfUsers;
    private double reportsNoDivUsersNo;
    private LocalDate date;
    private Vehicle vehicle;
    private int numberOfViolationsOfVehicle;
    private ViolationType violationType;
    private StatisticType statisticType;

    public Statistic(StatisticType statisticType) {
        this.statisticType = statisticType;
    }

    public Place getStreet() {
        return street;
    }

    public int getNumberOfViolationsInStreet() {
        return numberOfViolationsInStreet;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public LocalDate getDate() {
        return date;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public int getNumberOfViolationsOfVehicle() {
        return numberOfViolationsOfVehicle;
    }

    public ViolationType getViolationType() {
        return violationType;
    }

    public StatisticType getStatisticType() {
        return statisticType;
    }

    public void setStreet(Place street) {
        this.street = street;
    }

    public void setNumberOfViolationsInStreet(int numberOfViolationsInStreet) {
        this.numberOfViolationsInStreet = numberOfViolationsInStreet;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setNumberOfViolationsOfVehicle(int numberOfViolationsOfVehicle) {
        this.numberOfViolationsOfVehicle = numberOfViolationsOfVehicle;
    }

    public void setViolationType(ViolationType violationType) {
        this.violationType = violationType;
    }

    public double getReportsNoDivUsersNo() {
        return reportsNoDivUsersNo;
    }

    public void setReportsNoDivUsersNo(double reportsNoDivUsersNo) {
        this.reportsNoDivUsersNo = reportsNoDivUsersNo;
    }

    public List<Coordinate> getCoordinateListForStreet() {
        return new ArrayList<>(coordinateListForStreet);
    }

    public void setCoordinateListForStreet(List<Coordinate> coordinateListForStreet) {
        this.coordinateListForStreet = new ArrayList<>(coordinateListForStreet);
    }

}
