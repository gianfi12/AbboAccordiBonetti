package com.SafeStreets.model;

import com.google.gson.Gson;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * It represents a statistic.
 * For the requirement R10A
 * the statisticType is STREETS_STAT and the attributes street, coordinateListForStreet and
 * numberOfViolationsInStreet are used.
 * For the requirement R10B:
 * the statisticType is EFFECTIVENESS_STAT and the attributes numberOfReports, numberOfUsers,
 * reportsNoDivUsersNo and date are used.
 * For the requirement R10C:
 * the statisticType is VEHICLES_STAT and the attributes vehicle and numberOfViolationsOfVehicle are used.
 * For the requirement R10D:
 * the statisticType is VIOLATIONS_STAT and the attribute violationType is used.
 */
public class Statistic {
    /**
     * Address of the street
     */
    private String street;
    /**
     * List of coordinates in which the reports has been made
     */
    private List<Coordinate> coordinateListForStreet;
    /**
     * Number of reported violations in the street
     */
    private int numberOfViolationsInStreet;

    /**
     * Number of reports in the database at the specific date
     */
    private int numberOfReports;
    /**
     * Number of users in the database at the specific date
     */
    private int numberOfUsers;
    /**
     * The number of reports in the database at the specific date divide by the number of users in the database at the specific date
     */
    private double reportsNoDivUsersNo;
    /**
     * Date at which the statistic EFFECTIVENESS_STAT refers
     */
    private LocalDate date;

    /**
     * Vehicle
     */
    private Vehicle vehicle;
    /**
     * Number of violations that the vehicle has committed
     */
    private int numberOfViolationsOfVehicle;

    /**
     * Type of violation
     */
    private ViolationType violationType;

    /**
     * Type of statistic
     */
    private StatisticType statisticType;

    /**
     * It creates one Statistic with the given statistic type
     * @param statisticType new statistic type
     */
    public Statistic(StatisticType statisticType) {
        this.statisticType = statisticType;
    }

    /**
     * It returns the numberOfViolationsInStreet
     * @return numberOfViolationsInStreet
     */
    public int getNumberOfViolationsInStreet() {
        return numberOfViolationsInStreet;
    }
    /**
     * It returns the numberOfViolationsInStreet
     * @return numberOfViolationsInStreet
     */
    public int getNumberOfReports() {
        return numberOfReports;
    }
    /**
     * It returns the getNumberOfUsers
     * @return getNumberOfUsers
     */
    public int getNumberOfUsers() {
        return numberOfUsers;
    }
    /**
     * It returns the date
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }
    /**
     * It returns the vehicle
     * @return vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }
    /**
     * It returns the numberOfViolationsOfVehicle
     * @return numberOfViolationsOfVehicle
     */
    public int getNumberOfViolationsOfVehicle() {
        return numberOfViolationsOfVehicle;
    }
    /**
     * It returns the violationType
     * @return violationType
     */
    public ViolationType getViolationType() {
        return violationType;
    }
    /**
     * It returns the statisticType
     * @return statisticType
     */
    public StatisticType getStatisticType() {
        return statisticType;
    }

    /**
     * It sets the numberOfViolationsInStreet with the new one
     * @param numberOfViolationsInStreet new numberOfViolationsInStreet
     */
    public void setNumberOfViolationsInStreet(int numberOfViolationsInStreet) {
        this.numberOfViolationsInStreet = numberOfViolationsInStreet;
    }
    /**
     * It sets the numberOfReports with the new one
     * @param numberOfReports new v
     */
    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }
    /**
     * It sets the numberOfUsers with the new one
     * @param numberOfUsers new numberOfUsers
     */
    public void setNumberOfUsers(int numberOfUsers) {
        this.numberOfUsers = numberOfUsers;
    }
    /**
     * It sets the date with the new one
     * @param date new date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
    /**
     * It sets the vehicle with the new one
     * @param vehicle new vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    /**
     * It sets the numberOfViolationsOfVehicle with the new one
     * @param numberOfViolationsOfVehicle new numberOfViolationsOfVehicle
     */
    public void setNumberOfViolationsOfVehicle(int numberOfViolationsOfVehicle) {
        this.numberOfViolationsOfVehicle = numberOfViolationsOfVehicle;
    }
    /**
     * It sets the violationType with the new one
     * @param violationType new violationType
     */
    public void setViolationType(ViolationType violationType) {
        this.violationType = violationType;
    }




    /**
     * It returns the reportsNoDivUsersNo
     * @return reportsNoDivUsersNo
     */
    public double getReportsNoDivUsersNo() {
        return reportsNoDivUsersNo;
    }
    /**
     * It sets the reportsNoDivUsersNo with the new one
     * @param reportsNoDivUsersNo new reportsNoDivUsersNo
     */
    public void setReportsNoDivUsersNo(double reportsNoDivUsersNo) {
        this.reportsNoDivUsersNo = reportsNoDivUsersNo;
    }
    /**
     * It returns the coordinateListForStreet
     * @return coordinateListForStreet
     */
    public List<Coordinate> getCoordinateListForStreet() {
        return new ArrayList<>(coordinateListForStreet);
    }
    /**
     * It sets the coordinateListForStreet with the new one
     * @param coordinateListForStreet new coordinateListForStreet
     */
    public void setCoordinateListForStreet(List<Coordinate> coordinateListForStreet) {
        this.coordinateListForStreet = new ArrayList<>(coordinateListForStreet);
    }
    /**
     * It returns the street
     * @return street
     */
    public String getStreet() {
        return street;
    }
    /**
     * It sets the street with the new one
     * @param street new street
     */
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

    /**
     * This is the class used to transform the an instance of the statistic class to an intermediate from that can be used to easily serialize the Statistic to a json string
     */
    class StatisticSend{
        /**
         * These are the same attributes that are present in the above statistic class
         */
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

        /**
         * Is the constructor of the StatisticSend class that takes the same elements as of the Statistic class, but in this case the elements are represents in a serializable way
         */
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
