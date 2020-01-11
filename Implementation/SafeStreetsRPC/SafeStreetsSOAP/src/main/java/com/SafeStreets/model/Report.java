package com.SafeStreets.model;

import com.SafeStreets.mapsserviceadapter.FieldsException;
import com.SafeStreets.mapsserviceadapter.GeocodeException;
import com.SafeStreets.mapsserviceadapter.MapsServiceInterface;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * It represents a report done by the user
 */
public class Report {
    /**
     * This is used to print log message if an error occurs
     */
    private final static Logger LOGGER = Logger.getLogger(Report.class.getName());
    /**
     * Time of when the report has been done
     */
    private OffsetDateTime reportOffsetDateTime;
    /**
     * Time of when the violation has been seen, if it is null then it is assumed that the report
     * has been done at the moment in which the violation has been seen
     */
    private OffsetDateTime odtOfWatchedViolation;
    /**
     * Place in which the violation happen
     */
    private Place place;
    /**
     * Type of the reported violation
     */
    private ViolationType violationType;
    /**
     * Description of the report
     */
    private String description;
    /**
     * Vehicle that has been accused to have done the violation
     */
    private Vehicle vehicle;

    /**
     * It constructs one Report with the given parameters
     * @param reportOffsetDateTime Time of when the report has been done
     * @param odtOfWatchedViolation Time of when the violation has been seen, if it is null then it is assumed that the report
     * has been done at the moment in which the violation has been seen
     * @param place Place in which the violation happen
     * @param violationType Type of the reported violation
     * @param description Description of the report
     * @param vehicle Vehicle that has been accused to have done the violation
     */
    public Report(OffsetDateTime reportOffsetDateTime, OffsetDateTime odtOfWatchedViolation, Place place, ViolationType violationType, String description, Vehicle vehicle) {
        this.reportOffsetDateTime = reportOffsetDateTime;
        this.odtOfWatchedViolation = odtOfWatchedViolation;
        this.place = place;
        this.violationType = violationType;
        this.description = description;
        this.vehicle = vehicle;
    }

    /**
     * It returns the reportOffsetDateTime
     * @return reportOffsetDateTime
     */
    public OffsetDateTime getReportOffsetDateTime() {
        return reportOffsetDateTime;
    }
    /**
     * It returns the reportOffsetDateTime
     * @return reportOffsetDateTime
     */
    public OffsetDateTime getOdtOfWatchedViolation() {
        return odtOfWatchedViolation;
    }
    /**
     * It returns the place
     * @return place
     */
    public Place getPlace() {
        return place;
    }
    /**
     * It returns the violationType
     * @return violationType
     */
    public ViolationType getViolationType() {
        return violationType;
    }
    /**
     * It returns the description
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     * It returns the vehicle
     * @return vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * It sets the place with the new one
     * @param place new place
     */
    public void setPlace(Place place) {
        this.place = place;
    }

    /**
     * This method is used to get an instance of a Report from a json string that follows the structure define in this method
     * @param jsonstring Is the json string that represents the report
     * @return Is the reconstructed Report
     */
    public static Report fromJSON(String jsonstring){
        JSONObject obj = new JSONObject(jsonstring);
        String reportOffsetDateTime = obj.getString("reportOffsetDateTime");
        reportOffsetDateTime  = reportOffsetDateTime.substring(0,reportOffsetDateTime.lastIndexOf("."));
        String odtOfWatchedViolation = obj.getString("odtOfWatchedViolation");
        String place = obj.getString("place");
        String violationType = obj.getString("violationType");
        violationType = violationType.split("\\.")[1];
        String description = obj.getString("description");
        String vehicle = obj.getString("vehicle");

        ZoneId zoneId= TimeZone.getTimeZone("Europe/Rome").toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(reportOffsetDateTime, formatter);
        Instant instant = dateTime.atZone(ZoneId.of("Europe/Paris")).toInstant();
        OffsetDateTime rodt = OffsetDateTime.ofInstant(instant, zoneId);

        OffsetDateTime odt=null;
        if(!odtOfWatchedViolation.equals("")) {
            odtOfWatchedViolation  = odtOfWatchedViolation.substring(0,odtOfWatchedViolation.lastIndexOf("."));
            dateTime = LocalDateTime.parse(odtOfWatchedViolation, formatter);
            instant = dateTime.atZone(ZoneId.of("Europe/Paris")).toInstant();
            odt = OffsetDateTime.ofInstant(instant, zoneId);
        }

        MapsServiceInterface mapsService = MapsServiceInterface.getInstance();
        try {
            Place placeViolation=null;
            if(!place.equals("")) {
                placeViolation = mapsService.geocoding(place);
            }else{
                Double devicePositionLat = obj.getDouble("devicePositionLat");
                Double devicePositionLng = obj.getDouble("devicePositionLng");
                placeViolation = mapsService.geocoding(new Coordinate(devicePositionLat,devicePositionLng,null));
            }

            return new Report(rodt, odt, placeViolation, ViolationType.valueOf(violationType),description, new Vehicle(vehicle));
        }catch (GeocodeException | FieldsException e){
            LOGGER.log(Level.SEVERE,"Error with violation place!!");
        }
        return null;
    }

    /**
     * This method is used to serialize the Report object as a json string with the following structure
     * @param report Is the instance of the report that has to be transformed into a json string
     * @return Is the json string that represents the json object
     */
    public static JSONObject toJSON(Report report){
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        if(report.getReportOffsetDateTime()!=null) {
            jsonObject.put("reportOffsetDateTime",report.getReportOffsetDateTime().toString());
        }else{
            jsonObject.put("reportOffsetDateTime","");
        }
        if(report.getOdtOfWatchedViolation()!=null) {
            jsonObject.put("odtOfWatchedViolation", report.getOdtOfWatchedViolation().toString());
        }else{
            jsonObject.put("odtOfWatchedViolation", "");
        }
        Type type = new TypeToken<Place>(){}.getType();
        jsonObject.put("place",gson.toJson(report.getPlace(),type));
        jsonObject.put("violationType",report.getViolationType().toString());
        jsonObject.put("description",report.getDescription());
        jsonObject.put("vehicle",report.getVehicle().getLicensePlate());

        return  jsonObject;
    }

    /**
     * This method is used to correct the plate of the vehicle if it is wrong
     * @param vehicle Is the new plate of the vehicle
     */
    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
}
