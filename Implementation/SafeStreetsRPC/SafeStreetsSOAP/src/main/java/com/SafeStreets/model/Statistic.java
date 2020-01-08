package com.SafeStreets.model;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Statistic {
    private String street;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String toJSON(){
        Gson gson = new Gson();
        List<String> coordinates = new ArrayList<>();
        if(coordinateListForStreet!=null) {
            for (Coordinate coordinate : coordinateListForStreet) {
                coordinates.add(gson.toJson(coordinate));
            }
        }
        return gson.toJson(new StatisticSend((street==null?"":street),coordinates,numberOfViolationsInStreet,numberOfReports,numberOfUsers,reportsNoDivUsersNo,(date==null?"":date.toString()),(vehicle==null? "":vehicle.getLicensePlate()),numberOfViolationsOfVehicle,(violationType==null? "": violationType.toString()),statisticType.toString()));
    }

    class StatisticSend{
        private String street;
        private List<String> coordinateListForStreet;
        private int numberOfViolationsInStreet;
        private int numberOfReports;
        private int numberOfUsers;
        private double reportsNoDivUsersNo;
        private String date;
        private String vehicle;
        private int numberOfViolationsOfVehicle;
        private String violationType;
        private String statisticType;

        public StatisticSend(String street, List<String> coordinateListForStreet, int numberOfViolationsInStreet, int numberOfReports, int numberOfUsers, double reportsNoDivUsersNo, String date, String vehicle, int numberOfViolationsOfVehicle, String violationType, String statisticType) {
            this.street = street;
            this.coordinateListForStreet = coordinateListForStreet;
            this.numberOfViolationsInStreet = numberOfViolationsInStreet;
            this.numberOfReports = numberOfReports;
            this.numberOfUsers = numberOfUsers;
            this.reportsNoDivUsersNo = reportsNoDivUsersNo;
            this.date = date;
            this.vehicle = vehicle;
            this.numberOfViolationsOfVehicle = numberOfViolationsOfVehicle;
            this.violationType = violationType;
            this.statisticType = statisticType;
        }
    }
}
