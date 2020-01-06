package com.SafeStreets.model;

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

public class Report {
    /**
     * This is used to print log message if an error occurs
     */
    private final static Logger LOGGER = Logger.getLogger(Report.class.getName());
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

    public static Report fromJSON(String jsonstring){
        JSONObject obj = new JSONObject(jsonstring);
        String reportOffsetDateTime = obj.getString("reportOffsetDateTime");
        String odtOfWatchedViolation = obj.getString("odtOfWatchedViolation");
        String place = obj.getString("place");
        String violationType = obj.getString("violationType");
        String description = obj.getString("description");
        String vehicle = obj.getString("vehicle");

        ZoneId zoneId= TimeZone.getTimeZone("Europe/Rome").toZoneId();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(reportOffsetDateTime, formatter);
        Instant instant = dateTime.atZone(ZoneId.of("Europe/Paris")).toInstant();
        OffsetDateTime rodt = OffsetDateTime.ofInstant(instant, zoneId);

        dateTime = LocalDateTime.parse(odtOfWatchedViolation, formatter);
        instant = dateTime.atZone(ZoneId.of("Europe/Paris")).toInstant();
        OffsetDateTime odt = OffsetDateTime.ofInstant(instant,zoneId);

        MapsServiceInterface mapsService = MapsServiceInterface.getInstance();
        try {
            Place placeViolation = mapsService.geocoding(place);

            return new Report(rodt, odt, placeViolation, ViolationType.valueOf(violationType.split("\\.")[1]),description, new Vehicle(vehicle));
        }catch (GeocodeException e){
            LOGGER.log(Level.SEVERE,"Error with violation place!!");
        }
        return null;
    }

    public JSONObject toJSON(){
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("reportOffsetDateTime",reportOffsetDateTime.toString());
        jsonObject.put("odtOfWatchedViolation",odtOfWatchedViolation.toString());
        Type type = new TypeToken<Place>(){}.getType();
        jsonObject.put("place",gson.toJson(place,type));
        jsonObject.put("violationType",violationType.toString());
        jsonObject.put("description",description);
        jsonObject.put("vehicle",vehicle.getLicensePlate());

        return  jsonObject;
    }
}
