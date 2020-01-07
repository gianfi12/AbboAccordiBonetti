package com.SafeStreets.model;

import com.SafeStreets.modelEntities.CoordinateEntity;

import java.math.BigDecimal;

public class Coordinate {
    private Double latitude;
    private Double longitude;
    private Double altitude;

    public Coordinate(Double latitude, Double longitude, Double altitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public void setAltitude(Double altitude) {
        this.altitude = altitude;
    }

    public CoordinateEntity toCoordinateEntity() {
        CoordinateEntity coordinateEntity=new CoordinateEntity();
        coordinateEntity.setLatitude(new BigDecimal(latitude.toString()));
        coordinateEntity.setLongitude(new BigDecimal(longitude.toString()));
        if(altitude!=null)
            coordinateEntity.setAltitude(new BigDecimal(altitude.toString()));

        return coordinateEntity;
    }

    public boolean isEqual(Coordinate coordinateToCompare) {
        if(altitude==null && coordinateToCompare.altitude!=null ||
                altitude!=null && coordinateToCompare.altitude==null)
            return false;

        if(altitude==null)
            return latitude.equals(coordinateToCompare.latitude)&&longitude.equals(coordinateToCompare.longitude);

        return latitude.equals(coordinateToCompare.latitude)&&longitude.equals(coordinateToCompare.longitude) &&
                altitude.equals(coordinateToCompare.altitude);

    }

    @Override
    public String toString() {
        return "lat: "+latitude+", long: "+longitude+", alt: "+altitude;
    }
}
